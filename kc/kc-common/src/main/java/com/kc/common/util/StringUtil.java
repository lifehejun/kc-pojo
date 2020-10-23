package com.kc.common.util;

import com.kc.common.consts.CommConst;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtil  
 * @Description: TODO 字符串工具类
 * @author jason  
 * @date 2018-1-11
 */
public class StringUtil extends StringUtils {

	/**
	 * 类似log4j的字符串模板拼接
	 * @param string
	 * @param args
	 * @return
	 */
	public static String format(String string , Object... args){
		if(isEmpty(string)){
			return null;
		}
		if(args == null){
			args = new Object[0];
		}
		for(int i = 0; i < args.length; i++){
			string = string.replaceFirst("\\{\\}", args[i] == null ? "" : args[i].toString());
		}
		return string;
	}

	/**
	 * @Title: getUUID
	 * @Description: TODO 产生紧凑型32位UUID
	 * @return String
	 * @throws
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	/**
	 * str为空则返回str1,否则返回str
	 * @param str
	 * @param str1
	 * @return
	 */
	public static String isEmpty(String str,String str1){
		if(str == null || str == ""){
			return str1;
		}
		return str;
	}

	/**
	 *
	 * @Title: replaceBlank
	 * @Description: 替换空格制表符，换行符，空格
	 * @param str
	 * @return String
	 * @throws
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * @Title: replaceBlankAndSplit
	 * @Description: 替换空格制表符，换行符，空格
	 * @param str
	 * @return String
	 * @throws
	 */
	public static Set<String> replaceBlankAndSplit(String strs, String regex) {
		if (strs != null) {
			return new HashSet<String>(Arrays.asList(replaceBlank(strs).split(regex)));
		} else {
			return new HashSet<String>();
		}
	}


	/**
	 * 生成32位MD5加密秘钥key
	 * @return
	 */
	public static String createPriKey() {
		int  KeyLength = 32;
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" ;
		Random random = new Random();
		StringBuffer Keysb = new StringBuffer();
		for(int i = 0; i<KeyLength; i++){
			int number = random.nextInt(base.length());
			Keysb.append(base.charAt(number));
		}
		return Keysb.toString();
	}

	/**
	 * 谷歌身份验证key
	 * @return
	 */
	public static String createGoogleKey() {
		int  KeyLength = 8;
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" ;
		Random random = new Random();
		StringBuffer Keysb = new StringBuffer();
		for(int i = 0; i<KeyLength; i++){
			int number = random.nextInt(base.length());
			Keysb.append(base.charAt(number));
		}
		return Keysb.toString();
	}


	/**
	 * 判断字符串是否为整形
	 * @param string
	 * @return
	 */
	public static boolean isInt(String string) {
		if (string == null)
			return false;

		String regEx1 = "[\\-|\\+]?\\d+";
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx1);
		m = p.matcher(string);
		if (m.matches())
			return true;
		else
			return false;
	}



	/**
	 * 根据数字的不同长度，来进行替换 ，达到保密效果,该方法之针对数字类型有用，比如手机号码，银行卡
	 * @param str 替换的数字
	 * @return 替换后的数字
	 */
	public static String replaceWithNumStar(String str) {
		String numAfterReplaced = "";
		if (org.apache.commons.lang3.StringUtils.isBlank(str)){
			str = "";
		}
		int nameLength = str.length();

		if (nameLength <= 1) {
			numAfterReplaced = "*";
		} else if (nameLength == 2) {
			numAfterReplaced = replaceAction(str, "(?<=\\d{0})\\d(?=\\d{1})");
		} else if (nameLength >= 3 && nameLength <= 6) {
			numAfterReplaced = replaceAction(str, "(?<=\\d{1})\\d(?=\\d{1})");
		} else if (nameLength == 7) {
			numAfterReplaced = replaceAction(str, "(?<=\\d{1})\\d(?=\\d{2})");
		} else if (nameLength == 8) {
			numAfterReplaced = replaceAction(str, "(?<=\\d{2})\\d(?=\\d{2})");
		} else if (nameLength == 9) {
			numAfterReplaced = replaceAction(str, "(?<=\\d{2})\\d(?=\\d{3})");
		} else if (nameLength == 10) {
			numAfterReplaced = replaceAction(str, "(?<=\\d{3})\\d(?=\\d{3})");
		} else if (nameLength >= 11) {
			numAfterReplaced = replaceAction(str, "(?<=\\d{3})\\d(?=\\d{4})");
		}
		return numAfterReplaced;
	}

	/**
	 * 执行替换操作
	 * @param username
	 * @param regular
	 * @return
	 */
	private static String replaceAction(String username, String regular) {
		return username.replaceAll(regular, "*");
	}



	/**
	 * @Description 字符串向左截取
	 * @author ShengLiu
	 * @date 2018/7/4
	 * @param str
	 * @param len
	 * @return java.lang.String
	 */
	public static String left(String str, int len) {
		if(org.apache.commons.lang3.StringUtils.isBlank(str)){
			return "";
		}
		if (len < CommConst.ZERO) {
			return CommConst.EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(CommConst.ZERO, len);

	}

	/**
	 * @Description 字符串向右截取
	 * @author ShengLiu
	 * @date 2018/7/4
	 * @param str
	 * @param len
	 * @return java.lang.String
	 */
	public static String right(String str, int len) {
		if(org.apache.commons.lang3.StringUtils.isBlank(str)){
			return "";
		}
		if (len < CommConst.ZERO) {
			return CommConst.EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);

	}

	/**
	 * @Description 根据不同名字的长度返回不同的显示数据,一般替换中文姓名，其他英文长的超过个3个字符则不支持
	 * @date 2018/7/4
	 * @param str
	 * @return java.lang.String
	 */
	public static String checkNameLength(String str){
		if(org.apache.commons.lang3.StringUtils.isBlank(str)){
			return "";
		}
		if(str.length() == CommConst.ONE) {
			return str;
		}else if(str.length() == CommConst.TWO){
			return "*" + right(str, CommConst.ONE);
		}else if(str.length() == CommConst.THREE){
			return left(str, CommConst.ONE) + "*" + right(str, CommConst.ONE);
		}else if(str.length() == CommConst.FOUR){
			return left(str, CommConst.ONE) + "**" + right(str, CommConst.ONE);
		}
		return str;
	}

	/**
	 * 用户账号星号替换
	 * @param str
	 * @return
	 */
	public static String checkNameStar(String str){
		if(org.apache.commons.lang3.StringUtils.isBlank(str)){
			return "";
		}
		if(str.length() >= 3 && str.length()<6) {
			return str.substring(0,1)+"***"+str.substring(2,str.length());
		}else if(str.length()>=6 && str.length()<10){
			return str.substring(0,3)+"***"+str.substring(6,str.length());
		}else if(str.length()>=10){
			return str.substring(0,3)+"***"+str.substring(9,str.length());
		}
		return str;
	}


	/**
	 * 生成用户名昵称
	 * @param len
	 * @return
	 */
	public static String getRandomJianHan(int len) {
		String ret = "";
		for (int i = 0; i < len; i++) {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				str = new String(b, "GBK"); // 转成中文
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}




}
