package com.kc.common.mail;

/**
 * @ClassName: MailConfig  
 * @Description: TODO 邮件参数类  
 * @author jason  
 * @date 2018-1-23
 */
public class MailConfig {	

	/**
	 * 发件人
	 */
	private String from;
	
	/**
	 * 收件人
	 */
    private String to;	
    
	/**
	 * 抄送
	 */
	private String cc;
		
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}	
}
