package com.kc.common.util;

import com.kc.common.exception.ApiException;
import com.kc.common.resp.BusinessCode;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ValidatorUtil  
 * @Description: TODO 验证工具类
 * @author jason  
 * @date 2018-1-26
 */
public class ValidatorUtil {

	/**
	 * @Title: validateMatch  
	 * @Description: TODO 正则匹配
	 * @param str
	 * @param regex
	 * @return void
	 * @throws
	 */
	public static void validateMatch(String str, String regex,
									 BusinessCode businessCode) throws ApiException {
		if (!StringUtils.trimToEmpty(str).matches(regex)) {
			throw new ApiException(businessCode.getCode(),businessCode.getMsg());
		}
	}

	/**
	 * @Title: validateNotEmpty
	 * @Description: TODO 验证字符串是否为空
	 * @param str
	 * @return void
	 * @throws
	 */
	public static void validateNotEmpty(String str, BusinessCode businessCode) throws ApiException {
		if (StringUtil.isBlank(str)) {
			throw new ApiException(businessCode.getCode(),businessCode.getMsg());
		}
	}


	/**
	 * @Title: validateUserName
	 * @Description: TODO 验证用户名:用户名为手机号
	 * @throws ApiException
	 * @return void
	 * @throws
	 */
	public static void validateUserName(String str) throws ApiException{
		validateNotEmpty(str, BusinessCode.USER_RESP_2009);
		validateMatch(str, Regex.PHONE, BusinessCode.USER_RESP_2012);
	}


	/**
	 * @Title: validatePhone  
	 * @Description: TODO 验证手机号码
	 * @param str
	 * @throws ApiException
	 * @return void
	 * @throws
	 */
	public static void validatePhone(String str) throws ApiException{
		validateNotEmpty(str, BusinessCode.USER_RESP_2009);
		validateMatch(str, Regex.PHONE, BusinessCode.USER_RESP_2012);
	}
	
	/**
	 * @Title: validatePassword  
	 * @Description: TODO 验证登录密码
	 * @param str
	 * @throws ApiException
	 * @return void
	 * @throws
	 */
	public static void validatePassword(String str) throws ApiException{
		validateNotEmpty(str, BusinessCode.USER_RESP_2003);
		validateMatch(str, Regex.PASSWORD, BusinessCode.USER_RESP_2013);
	}
	
	/**
	 * @Title: validateBankCard  
	 * @Description: TODO 验证银行卡
	 * @param str
	 * @throws ApiException
	 * @return void
	 * @throws
	 */
	public static void validateBankCard(String str) throws ApiException{
		validateNotEmpty(str, BusinessCode.USER_RESP_2010);
		validateMatch(str, Regex.BANK_CARD, BusinessCode.USER_RESP_2014);
	}
	
	/**
	 * @Title: validateCashPassword  
	 * @Description: TODO 验证提现密码
	 * @param str
	 * @throws ApiException
	 * @return void
	 * @throws
	 */
	public static void validateCashPwd(String str) throws ApiException{
		validateNotEmpty(str, BusinessCode.USER_RESP_2008);
		validateMatch(str, Regex.CASH_PASSWROD, BusinessCode.USER_RESP_2015);
	}

	/**
	 * 验证短信验证码不为空并且为数字
	 * @param str
	 * @throws ApiException
	 */
	public static void validateVerifyCode(String str) throws ApiException{
		if(StringUtils.isBlank(str)){
			throw new ApiException(BusinessCode.USER_RESP_2025.getCode());
		}
		validateMatch(str, Regex.NUMBER, BusinessCode.USER_RESP_2026);
	}



	public static void main(String[] args)  throws ApiException {
		System.out.println(StringUtil.getRandomJianHan(3));
	}

	}

class Regex{
	
	/** 用户名 **/
	public static final String USERNAME = "^(?![0-9]+$)[0-9A-Za-z]{3,16}$";
	
	/**身份证号*/
	public static final String ID_NO = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
	
	/**手机号码*/
	public static final String PHONE = "^[1][3,4,5,7,8]+\\d{9}$";
	
	/**汉字*/
	public static final String CHINESE = "^[\u4e00-\u9fa5]+$";
	
	/** 邮件 **/
	public static final String EMAIL = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
	
	/** url **/
	public static final String URL = "[a-zA-z]+://[^\\s]*";
	
	/** 密码 **/
	public static final String PASSWORD = "^[^\\s]{6,15}$";
	
	/** 提现密码 **/
	public static final String CASH_PASSWROD = "^\\d{4}$";
	
	/** 银行卡号 **/
	public static final String BANK_CARD = "^\\d{16,25}$";

	/** 数字 **/
	public static final String NUMBER = "^[0-9]*$";
	
}