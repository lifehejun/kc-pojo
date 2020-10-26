package com.kc.common.exception;

import com.kc.common.resp.BusinessCode;

import java.text.MessageFormat;

/**
 * @ClassName: AppException  
 * @Description: 应用异常
 * @author jason  
 * @date 2018-4-20
 */
public class ApiException extends RuntimeException{

	/**
	 * @Fields field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 2706439819927695897L;
	private String msg;
	private String code;
	private Object[] args;


	public ApiException(String code){
		this.code = code;
		this.msg = BusinessCode.getMsg(code);
	}
	public ApiException(String code,Object[] args){
		this.code = code;
		this.msg = MessageFormat.format(BusinessCode.getMsg(code),args);
	}

	public ApiException(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public ApiException(String code,String msg,Object... args){
		this.code = code;
		this.msg = MessageFormat.format(msg,args);
		this.args = args;
	}
	/**
	 * @param cause
	 */
	public ApiException(Throwable cause){
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApiException(String message, Throwable cause){
		super(message, cause);
	}
	
	@Override
	public String toString(){
		return this.msg;
	}
	
	@Override
	public String getMessage(){
		return toString();
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
