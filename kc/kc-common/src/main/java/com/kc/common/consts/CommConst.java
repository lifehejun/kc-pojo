package com.kc.common.consts;

import com.kc.common.enums.VerifyCodeTypeEnums;
import com.kc.common.util.PropertiesUtil;

import java.beans.BeanInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @ClassName: CommConst  
 * @Description: TODO 常量类
 * @author jason  
 * @date 2018-1-12
 */
public class CommConst {
	
	private CommConst() {
		
	}

	/**
	 * 默认值信息
	 */
	public final static int DEFAULT_START_PAGE_NUM = 1;
	public final static int DEFAULT_INPUT_USER_PASSWORD_ERROR_TIMES = 5;
	public final static int DEFAULT_PAGE_SIZE = 20;
	public final static String DEFAULT_USER_ADDRESS = "火星喵";
	public final static String DEFAULT_USER_PASSWORD = "123456";
	public final static BigDecimal DEFAULT_MIN_CASH_MONEY = new BigDecimal(100);

	/**
	 * 用户敏感信息转换星号常量类
	 */
	public final static  String EMPTY = "";
	public final static  int ZERO = 0;
	public final static  int ONE = 1;
	public final static  int TWO = 2;
	public final static  int THREE = 3;
	public final static  int FOUR = 4;

	//评论内容字数最大长度
	public final static int COMMENT_CONT_MAX_LEN = 50;

	//用户状态
	public final static Integer USER_STATUS_0 = 0; //冻结
	public final static Integer USER_STATUS_1 = 1; //正常

	//用户推广激活状态
	public final static Integer USER_ACTIVE_STATUS_0 = 0 ; //未激活
	public final static Integer USER_ACTIVE_STATUS_1 = 1 ; //已激活

	//VIP视频等级
	public final static Integer USER_GRADE_0 = 0; //普通会员
	public final static Integer USER_GRADE_1 = 1; //白银
	public final static Integer USER_GRADE_2 = 2; //黄金
	public final static Integer USER_GRADE_3 = 3; //钻石
	public final static Integer USER_GRADE_4 = 4; //至尊


	public final static Integer TRANS_STATUS_0 = 0; //待交易
	public final static Integer TRANS_STATUS_1 = 1; //交易成功
	public final static Integer TRANS_STATUS_2 = 2; //交易失败

	public final static String COUPON_SELL_STATUS_NO_SELL = "未开售";
	public final static String COUPON_SELL_STATUS_SELLING = "销售中";
	public final static String COUPON_SELL_STATUS_PASS_SELL = "已过销售期";
	public final static String COUPON_SELL_STATUS_SELL_COMPLETE = "已售罄";


	public final static String COUPON_VALID_STATUS_NO_VALID = "未生效";
	public final static String COUPON_VALID_STATUS_VALIDING = "生效中";
	public final static String COUPON_VALID_STATUS_PASS_VALID = "已过期";





	//验证码类型参数集合
	public static List<String> VERIFY_CODE_TYPE = Arrays.asList(
			VerifyCodeTypeEnums.VERIFY_CODE_TYPE_REG.getCode(),VerifyCodeTypeEnums.VERIFY_CODE_TYPE_LOGIN.getCode());

}