package com.kc.common.web.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName: WebMonitorInterceptor  
 * @Description: TODO 日志监控拦截器
 * @author jason  
 * @date 2017-8-15
 */
public class WebMonitorInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = Logger.getLogger(WebMonitorInterceptor.class);
	
	private NamedThreadLocal<Long>  startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");  
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		 //记录请求日志
		if(logger.isDebugEnabled()){
			startTimeThreadLocal.set(System.currentTimeMillis());  //请求开始时间
			StringBuffer sb = new StringBuffer();
			sb.append("request URL: " + request.getServletPath());
			Map<String, String[]> params = request.getParameterMap();  
	        String queryString = "";  
	        for (String key : params.keySet()) {  
	            String[] values = params.get(key);  
	            for (int i = 0; i < values.length; i++) {  
	                String value = values[i];  
	                queryString += key + "=" + value + "&";  
	            }  
	        }  
	        if(StringUtils.trimToNull(queryString) != null){
	        	sb.append("\r\n--> request Params:");
	        	sb.append(queryString.substring(0, queryString.length() - 1));  
	        }
	        logger.debug(sb.toString());
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: postHandle</p>
	 * <p>Description: 后处理回调方法，实现处理器的后处理（但在渲染视图之前）</p> 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
	}
	
	
	/*
	 * (non-Javadoc)
	 * <p>Title: afterCompletion</p>
	 * <p>Description: </p> 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(logger.isDebugEnabled()){
			//记录请求处理耗时
	        long beginTime = startTimeThreadLocal.get();  
	        StringBuffer sb = new StringBuffer();
	    	sb.append("\r\n request URL: " + request.getServletPath());
	        sb.append(" executed in "
					+ (System.currentTimeMillis() - beginTime) + " msec");
	        logger.debug(sb.toString());
		}
	}

}
