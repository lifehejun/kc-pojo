package com.kc.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @ClassName: NumberUtil  
 * @Description: 数字工具类
 * @author jason  
 * @date 2018-4-13
 */
public class NumberUtil extends NumberUtils{
	
	public static final String ZERO_STR = "0.00";
	
	/**
	 * @Title: toInt  
	 * @Description: 将obj转为int类型
	 * @param obj
	 * @return
	 * @return int
	 * @throws
	 */
	public static int toInt(Object obj){
		return toInt(ObjectUtils.toString(obj));
	}

	/**
	 * @Title: toInt  
	 * @Description: 将obj转为int类型
	 * @param obj
	 * @param defaultValue
	 * @return
	 * @return int
	 * @throws
	 */
	public static int toInt(Object obj, int defaultValue){
		try{
			String value = ObjectUtils.toString(obj, defaultValue+"");
			return Integer.parseInt(value);
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	/**
	 * @Title: toDecimal  
	 * @Description:  将obj转为bigdecimal类型
	 * @param obj
	 * @param defaultValue
	 * @return
	 * @return BigDecimal
	 * @throws
	 */
	public static BigDecimal toDecimal(Object obj, BigDecimal defaultValue){
		String value = ObjectUtils.toString(obj);
		if(StringUtils.isEmpty(value)){
			return defaultValue;
		}
		return new BigDecimal(value);
	}
	
	
	/**
	 * @Title: add  
	 * @Description: obj类型的数字相加
	 * @param obj1
	 * @param obj2
	 * @return BigDecimal
	 * @throws
	 */
	public static BigDecimal add(Object obj1, Object obj2){
		BigDecimal d1 = toDecimal(obj1, BigDecimal.ZERO);
		BigDecimal d2 = toDecimal(obj2, BigDecimal.ZERO);
		return d1.add(d2);
	}
	
	/**
	 * @Title: add  
	 * @Description: obj类型的数字相加
	 * @param obj
	 * @return BigDecimal
	 * @throws
	 */
	public static BigDecimal add(Object... obj){
		if(obj != null && obj.length > 0){
			BigDecimal sum = BigDecimal.ZERO;
			for(int i = 0; i < obj.length; i++){
				sum = sum.add(toDecimal(obj[i], BigDecimal.ZERO));
			}
			return sum;
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * @Title: subDecimal  
	 * @Description: 
	 * @param objs
	 * @return BigDecimal
	 * @throws
	 */
	public static BigDecimal subDecimal(Object... objs){
		if(objs != null && objs.length > 0){
			BigDecimal num = toDecimal(objs[0], BigDecimal.ZERO) ;
			for(int i = 1; i < objs.length; i++){
				num = num.subtract(toDecimal(objs[i], BigDecimal.ZERO));
			}
			return num;
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * @Title: addInt  
	 * @Description: TODO(这里用一句话描述这个方法的作用)  
	 * @param numbers
	 * @return
	 * @return int
	 * @throws
	 */
	public static int addInt(Integer... numbers){
		if(numbers != null && numbers.length > 0){
			int sum = 0;
			for(int i = 0; i < numbers.length; i++){
				sum += toInt(numbers[i], 0);
			}
			return sum;
		}
		return 0;
	}
	
	
	
	/**
	 * @Title: max  
	 * @Description:  返回两个参数中较大的
	 * @param b1
	 * @param b2
	 * @return BigDecimal
	 * @throws
	 */
	public static BigDecimal max(BigDecimal b1, BigDecimal b2) {
		return b1.compareTo(b2) > 0 ? b1 : b2;
	}

	/**
	 * @Title: min  
	 * @Description: 返回两个参数中较小的
	 * @param b1
	 * @param b2
	 * @return BigDecimal
	 * @throws
	 */
	public static BigDecimal min(BigDecimal b1, BigDecimal b2) {
		return b1.compareTo(b2) > 0 ? b2 : b1;
	}
	
	
}
