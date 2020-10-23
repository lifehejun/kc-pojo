package com.kc.common.resp;

/**
 * @ClassName: ResponseCode  
 * @Description: TODO 响应码定义类
 * @author jason  
 * @date 2018-1-17
 */
public class ResponseCode {
	
	/**
	 * AJAX请求成功
	 */
    public static final String AJAX_SUCCESS = "200";
	
    /**
     * AJAX请求异常
     */
	public static final String AJAX_ERROR = "300";
	
	/**
	 * Session超时
	 */
	public static final String SESSION_TIME_OUT = "301";
	
	
	private ResponseCode() {
		
	}

}
