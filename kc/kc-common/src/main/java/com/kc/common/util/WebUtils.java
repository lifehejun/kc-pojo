package com.kc.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: WebUtils  
 * @Description: TODO web工具类
 * @author jason  
 * @date 2018-1-22
 */
public class WebUtils {


	/**
	 * @Title: getClientIp  
	 * @Description: TODO 获取客户端Ip  
	 * @param request
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtil.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtil.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}




}
