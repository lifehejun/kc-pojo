package com.kc.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kc.api.annotation.PassToken;
import com.kc.api.annotation.UserLoginToken;
import com.kc.api.utils.RequestUtil;
import com.kc.biz.bean.UserBean;
import com.kc.biz.service.ITokenService;
import com.kc.biz.service.IUserService;
import com.kc.common.consts.RespCode;
import com.kc.common.util.api.ReqParamsUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * @ClassName: ApiAuthInterceptor
 * @Description: TODO api权限拦截器
 */
public class ApiAuthInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = Logger.getLogger(ApiAuthInterceptor.class);

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private IUserService userService;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("reqUrl:"+request.getRequestURI());
		String token = request.getHeader("token");// 从 http 请求头中取出 token
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 检查是否有passtoken注释，有则跳过认证
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return super.preHandle(request, response, handler);
			}
		}
		// 检查有没有需要用户权限的注解
		if (method.isAnnotationPresent(UserLoginToken.class)) {
			UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
			if (userLoginToken.required()) {
				// 执行认证
				if (token == null) {
					sendJsonErrorMsg(response, "用户未登录");
					return false;
				}
				// 获取 token 中的 user id
				String userId;
				String phone;
				try {
					userId = JWT.decode(token).getAudience().get(0);
					phone = JWT.decode(token).getAudience().get(1);
				} catch (JWTDecodeException j) {
					sendJsonErrorMsg(response, "未找到用户登陆记录");
					return false;
				}
				if (!tokenService.refresh(phone,token)) {
					sendJsonErrorMsg(response, "用户登陆已过期");
					return false;
				}
				UserBean user = userService.queryByUserId(userId);
				if (user == null || user.getStatus() == 0) {
					tokenService.logout(token);
					sendJsonErrorMsg(response, "用户登陆失效或冻结");
					return false;
				}

				// 验证 token
				JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPwd())).build();
				String userName = user.getUserName();
				String userPhone = user.getPhone();
				try {
					jwtVerifier.verify(token);
					request.setAttribute("userId", userId);
					request.setAttribute("userName",userName);
					request.setAttribute("phone",userPhone);
				} catch (JWTVerificationException e) {
					tokenService.logout(token);
					sendJsonErrorMsg(response, "用户信息校验失败");
					return false;
				}
				return super.preHandle(request, response, handler);
			}
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * 将某个对象转换成json格式并发送到客户端
	 *
	 * @param response
	 * @throws Exception
	 */
	private void sendJsonErrorMsg(HttpServletResponse response, String msg) throws Exception {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
		resultMap.put("code", RespCode.TOKEN_INVALID);
		logger.error("用户权限校验异常 " + msg);
		resultMap.put("msg", msg);
		resultMap.put("data",null);
		writer.print(JSONObject.toJSONString(resultMap, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat));
		writer.close();
		response.flushBuffer();
	}
}