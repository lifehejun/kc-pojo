package com.kc.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @ClassName: GenerationUtil  
 * @Description: TODO 生成唯一性id的公共类
 * @author jason  
 * @date 2018-1-15
 */
public class GenerationUtil {

	private static final String SYMBOLS = "0123456789"; // 数字
	private static final Random RANDOM = new SecureRandom();
	
	private GenerationUtil() {
		
	}
	


	/**
	 * @Title: generationAccountId  
	 * @Description: TODO 生成 account表的acc_no 规则：15位用户号+00（两位默认值）+币种（3位）+账户类型（3位） +2为随机数 == 共25位  作为1级别账户的acct_no  
	 * @param userId
	 * @param currency
	 * @param acctType
	 * @param acctLevel
	 * @return String
	 * @throws
	 */
	public static  String generationAccountId(String userId, String currency,
			String acctType, String acctLevel) {
		if ("1".equals(acctLevel)) {
			return userId + "00" + currency + "999" + getThreeDig();
		} else {
			return userId + "00" + currency + acctType + getThreeDig();
		}
	}


	public static  String generationPlatOrderNo(){
		String seqNo = "F160";
		Random random = new Random();
		for (int i = 0; i < 9; i++) {
			seqNo += random.nextInt(10);
		}
		return seqNo;
	}



	/**
	 * @Title: getThreeDig  
	 * @Description: TODO 生成三位随机数
	 * @return int
	 * @throws
	 */
	public static int getThreeDig(){
		return new Random().nextInt(900)+100;
	}



	/**
	 * @Title: getSixDig
	 * @Description: TODO 生成6位随机数的邀请码
	 * @return int
	 * @throws
	 */
	public static int getSixDig(){
		return getDig(6);
	}


	/**
	 * @Title: getSixDig
	 * @Description: TODO 生成n位随机数的邀请码
	 * @return int
	 * @throws
	 */
	public static int getDig(int len){
		String random = RandomStringUtils.randomNumeric(len);
		return Integer.valueOf(random).intValue();
	}
	/**
	 * @Title: generationUserId
	 * @Description: TODO 获取8位用户ID
	 * @return String
	 * @throws
	 */
	public static  String generationUserId() {
		String seqNo = "85";
		String random = RandomStringUtils.randomNumeric(8);
		return seqNo + random;
	}


	/**
	 *生成商户ID
	 * @return
	 */
	public static  String generationMerchantId() {
		String seqNo = "180";
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
			seqNo += random.nextInt(10);
		}
		return seqNo;
	}


	/**
	 * 生成18位交易订单号
	 * @return
	 */
	public static  String getTransNo() {
		String seqNo = DateTools.getYmdhmsTime();
		String random = RandomStringUtils.randomNumeric(4);
		return seqNo+random;
	}


	/**
	 * 随机生成服务器登录密码
	 * length 位数
	 * @return
	 */
	public static String getServerRandomPwd(int length){
		//产生随机数
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		//循环length次
		for(int i=0; i<length; i++){
			//产生0-2个随机数，既与a-z，A-Z，0-9三种可能
			int number=random.nextInt(3);
			long result=0;
			switch(number){
				//如果number产生的是数字0；
				case 0:
					//产生A-Z的ASCII码
					result=Math.round(Math.random()*25+65);
					//将ASCII码转换成字符
					sb.append(String.valueOf((char)result));
					break;
				case 1:
					//产生a-z的ASCII码
					result=Math.round(Math.random()*25+97);
					sb.append(String.valueOf((char)result));
					break;
				case 2:
					//产生0-9的数字
					sb.append(String.valueOf
							(new Random().nextInt(10)));
					break;
			}
		}
		return sb.toString();
	}

	/**
	 * 生成主题编号
	 * @return
	 */
	public static String getTopicCode(){
		String random = RandomStringUtils.randomNumeric(4);
		return "TC-"+random;
	}

	/**
	 * 生成视频标签编号
	 * @return
	 */
	public static String getVideoLabelCode(){
		String random = RandomStringUtils.randomNumeric(4);
		return "VL-"+random;
	}

	/**
	 * 生成优惠券编号
	 * @return
	 */
	public static String getCouponCode(){
		String random = RandomStringUtils.randomNumeric(4);
		return "YHQ-"+random;
	}

	public static void main(String[] args) {
		System.out.println(RandomStringUtils.randomNumeric(5));
	}

}
