package com.kc.biz.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kc.biz.bean.UserBean;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.service.ITokenService;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.util.StringUtil;
import com.kc.common.util.api.EncryptUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

@Service("tokenService")
public class TokenServiceImpl implements ITokenService {

	@Resource
	private RedisUtil redisUtil;
	
	@Override
	public String getToken(UserBean user) {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, 30);
		String token = "";
		String rand = EncryptUtils.MD5(EncryptUtils.MD5(user.getPhone() + System.currentTimeMillis()) +  + System.currentTimeMillis());
        token = JWT.create().withAudience(user.getUserId(),user.getPhone(), rand)// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(user.getUserPwd()));// 以 password 作为 token 的密钥
		//redisUtil.setKeyAndValueTimeout(token, String.valueOf(Boolean.TRUE),  60*60*24); //第一次请求要存入缓存
		String authUserKey = RedisKeyEnums.AUTH_USER_TOKEN.getCode() + user.getPhone();
		redisUtil.setKeyAndValueTimeout(authUserKey, token,  60*60*24); //第一次请求要存入缓存
        return token;
	}

	@Override
	public boolean refresh(String phone,String token) {
		String authUserKey = RedisKeyEnums.AUTH_USER_TOKEN.getCode() + phone;
		//if(StringUtil.isBlank(redisUtil.getValueByKey(token))) return false;
		//redisUtil.setKeyAndValueTimeout(token, String.valueOf(Boolean.TRUE), 60 * 60); //有效期延长到一个小时
		if(StringUtil.isBlank(redisUtil.getValueByKey(authUserKey))) return false;
		redisUtil.setKeyAndValueTimeout(authUserKey, token, 60 * 60); //有效期延长到一个小时
		return true;
	}

	@Override
	public void logout(String token) {
		redisUtil.removeValueByKey(token);
	}


}
