package com.kc.pay.interceptor;

import com.kc.biz.bean.AdminUserBean;
import com.kc.biz.bean.BusConfig;
import com.kc.biz.service.IBusConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @ClassName: CommonInterceptor  
 * @Description: TODO 拦截器
 * @author jason  
 * @date 2018-1-11
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {  
	
    private final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    @Autowired
	private IBusConfigService busConfigService;
        
    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
    
    //敏感字段不记录日志
    private static List<String> ignoreLogNameList; 	 
    
    static {
		ignoreLogNameList = new ArrayList<String>();
		ignoreLogNameList.add("password");
	}
    
    @SuppressWarnings("unchecked")
	@Override    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception {
    	if (logger.isDebugEnabled()) {
			startTimeThreadLocal.set(System.currentTimeMillis()); // 请求开始时间
			StringBuffer sb = new StringBuffer();
			sb.append("request URL: " + request.getServletPath());
			Map<String, String[]> params = request.getParameterMap();
			String queryString = "";
			for (String key : params.keySet()) {
				if (!ignoreLogNameList.contains(key)) {
					String[] values = params.get(key);
					for (int i = 0; i < values.length; i++) {
						String value = values[i];
						queryString += key + "=" + value + "&";
					}
				}
			}
			if (StringUtils.trimToNull(queryString) != null) {
				sb.append("\r\n--> request Params:");
				sb.append(queryString.substring(0, queryString.length() - 1));
			}
			logger.debug(sb.toString());
		}
    	return true;
    }    
    
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {          
    }    
    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    @Override    
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (logger.isDebugEnabled()) {
			// 记录请求处理耗时
			long beginTime = startTimeThreadLocal.get();
			StringBuffer sb = new StringBuffer();
			sb.append("\r\n request URL: " + request.getServletPath());
			sb.append(" executed in " + (System.currentTimeMillis() - beginTime) + " msec");
			logger.debug(sb.toString());
		}
	}  
  
}  