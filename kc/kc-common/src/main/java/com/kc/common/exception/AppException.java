package com.kc.common.exception;
/**
 * @ClassName: AppException  
 * @Description: 应用异常
 * @author jason  
 * @date 2018-4-20
 */
public class AppException extends RuntimeException{
	
	/**  
	 * @Fields field:{todo}(用一句话描述这个变量表示什么)  
	 */  
	private static final long serialVersionUID = 2706439819927695897L;
	private String msg;

	
	public AppException(String msg){
		this.msg = msg;
	}
	
	/**
	 * @param cause
	 */
	public AppException(Throwable cause){
		super(cause);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public AppException(String message,Throwable cause){
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

}
