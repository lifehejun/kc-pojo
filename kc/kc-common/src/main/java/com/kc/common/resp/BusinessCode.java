package com.kc.common.resp;

import com.kc.common.enums.UserTypeEnums;
import org.apache.commons.lang.StringUtils;

/**
 * Created by AX on 2018/6/15.
 */
public enum BusinessCode {

    CONSUMER000001("000001", "请勿重复提交！"),

    PAY_RESP_500("500", "系统异常或平台正在维护"),
    PAY_RESP_0000("0000", "支付订单提交成功"),
    PAY_RESP_1001("1001","商户ID不能为空"),
    PAY_RESP_1002("1002","商户不存在"),
    PAY_RESP_1003("1003", "商户已冻结"),
    PAY_RESP_1004("1004", "商户订单号不能为空"),
    PAY_RESP_1005("1005", "订单金额不能为空"),
    PAY_RESP_1006("1006", "订单金额必须精确到分"),
    PAY_RESP_1007("1007", "订单金额不能小于2元"),
    PAY_RESP_1008("1008", "支付渠道编码不能为空"),
    PAY_RESP_1009("1009", "支付平台标识不能为空"),
    PAY_RESP_1010("1010", "异步通知地址不能为空"),
    PAY_RESP_1011("1011", "订单提交时间不能为空"),
    PAY_RESP_1012("1012", "签名类型不能为空"),
    PAY_RESP_1013("1013", "签名数据不能为空"),
    PAY_RESP_1014("1014", "验证签名不正确"),
    PAY_RESP_1015("1015", "支付渠道编码不存在"),
    PAY_RESP_1016("1016", "签名类型不存在,请默认填写MD5"),
    PAY_RESP_1017("1017", "支付平台标识不存在,请默认填写pc或者mobile"),


    USER_RESP_2001("2001", "APP登录异常"),
    USER_RESP_2002("2002", "登录用户名不能为空"),
    USER_RESP_2003("2003", "登录密码不能为空"),
    USER_RESP_2004("2004", "非法的登录密码"),
    USER_RESP_2005("2005", "用户不存在"),
    USER_RESP_2006("2006", "用户密码错误"),
    USER_RESP_2007("2007", "用户已冻结,禁止操作"),
    USER_RESP_2008("2008", "提现密码不能为空"),
    USER_RESP_2009("2009", "手机号不能为空"),
    USER_RESP_2010("2010", "银行卡号不能为空"),

    USER_RESP_2011("2011","用户名格式应为3-15位数字，字母组合！"),
    USER_RESP_2012("2012","手机号码格式不正确！"),
    USER_RESP_2013("2013","请输入6-15为字母、数字组合的登录密码！"),
    USER_RESP_2014("2014","银行卡号格式不正确！"),
    USER_RESP_2015("2015","提现密码必须为4位数字！"),
    USER_RESP_2016("2016", "注册出错，请稍候重试"),

    USER_RESP_2017("2017", "登录密码修改失败"),
    USER_RESP_2018("2018", "手机号绑定失败"),
    USER_RESP_2019("2019", "银行卡户名不能为空"),
    USER_RESP_2020("2020", "请选择开户银行"),
    USER_RESP_2021("2021", "银行绑定失败"),
    USER_RESP_2022("2022", "邀请码不存在,请联系好友或者客服索取"),
    USER_RESP_2023("2023", "该用户已注册,请主人换个手机号吧"),
    USER_RESP_2024("2024", "验证码类型错误"),
    USER_RESP_2025("2025", "注册验证码不能为空"),
    USER_RESP_2026("2026", "注册验证码必须为数字"),
    USER_RESP_2027("2027", "注册验证码错误或已失效"),
    USER_RESP_2028("2028", "用户为正常状态"),
    USER_RESP_2029("2029", "密码错误,已输入{0}次错误密码,最多5次"),
    USER_RESP_2030("2030", "您到账户因输错密码超过5次,系统暂时冻结账户,请您次日再次重试或者联系客服"),
    USER_RESP_2031("2031", "内部测试用户未配置,请先添加一个内部测试用户"),
    USER_RESP_2032("2032", "系统检查到您有恶意注册会员的异常,注册失败"),
    USER_RESP_2033("2033", "昵称不能为空"),
    USER_RESP_2034("2034", "昵称修改失败"),
    RECEIPT_TOOL_RESP_3001("3001", "APP端签名参数不能为空"),
    //RECEIPT_TOOL_RESP_3002("3002", "用户密码错误"),
    //RECEIPT_TOOL_RESP_3003("3003", "用户密码错误"),
    RECEIPT_TOOL_RESP_3004("3004", "请求签名错误"),
    RECEIPT_TOOL_RESP_3005("3005", "会话token获取失败"),
    RECEIPT_TOOL_RESP_3006("3006", "token已过期或者登录超时!请重新登录"),

    TRANS_RESP_4001("4001","该笔交易状态为已处理，操作失败"),
    TRANS_RESP_4002("4002","该笔交易金额必须大于0，操作失败"),
    TRANS_RESP_4003("4003","交易类型不能为空，操作失败"),
    TRANS_RESP_4004("4004","提现金额最低{0}元，操作失败"),
    TRANS_GOLD_COIN_RESP_4005("4005","该笔交易金币个数必须大于0，操作失败"),
    TRANS_GOLD_COIN_RESP_4006("4006","该笔交易金币个数必须为整数，操作失败"),
    TRANS_VIP_INFO_NULL_RESP_4007("4007","未查询到该VIP通道，开通失败"),

    COMMENT_BIZ_ID_NULL_5001("5001","评论对象业务ID不能为空"),
    COMMENT_CONT_NULL_5002("5002","评论内容不能为空"),
    COMMENT_CONT_LEN_5003("5003","评论内容长度不能超过50字符"),
    COMMENT_CONT_LEN_5004("5003","评论内容长度不能超过100字符"),
    COMMENT_TYPE_NULL_5005("5005","评论类型不能为空"),

    POST_TOPIC_CODE_MIN_1_5101("5101","至少选择1个主题标签"),
    POST_TOPIC_CODE_MAX_3_5102("5102","最多选择3个主题标签"),
    POST_TITLE_NULL_5103("5103","请输入帖子内容"),
    POST_TITLE_IS_FORBID_KEY_5104("5104","内部存在违规,违规词:{0}"),

    FOLLOW_YES_6001("6001","主人,您已关注过该用户了"),

    VIDEO_ID_NULL("7001","点赞视频Id不能为空"),

    FILTER_BLACK_LOGIN_8001("8001","该账号禁止登录操作"),
    FILTER_BLACK_REG_IP_8002("8002","该区域禁止非法注册"),
    FILTER_BLACK_REG_IP_8003("8003","该区域禁止非法登录"),
    FILTER_TEMPLATE_IMPORT_FAIL_8004("8004","过滤规则模板导入失败"),

    FILE_UPLOAD_NULL_8101("8101","上传的文件不能为空"),
    FILE_UPLOAD_TO_COS_FAIL_8102("8102","上传文件至腾讯COS失败"),
    FILE_UPLOAD_8103("8103","最少选择一个图片"),

    VIDEO_ID_NULL_8201("8201","视频ID参数为空"),

    COUPON_ID_NULL_8301("8301","优惠券代码参数为空"),
    COUPON_RECEIVE_FAIL_8302("8302","领取优惠券出错，请稍候重试"),
    COUPON_NOT_FIND_8303("8303","优惠券不存在"),
    COUPON_INVALID_8304("8304","优惠券已失效"),
    COUPON_SOLD_OUT_8305("8305","该优惠券已售罄"),
    COUPON_MAX_RECEIVE_NUM_8306("8306","该优惠券每个用户最多只能领取{0}张"),
    COUPON_NO_SELL_8307("8307","该优惠券未开售"),
    COUPON_PASS_SELL_8308("8308","该优惠券已过销售期"),

    GAME_RESP_9001("9001","游戏类型不存在"),
    GAME_RESP_9002("9002","游戏ID不存在");

    //返回业务编码
    private String code;
    //返回业务信息
    private String msg;

    // 构造方法
    private BusinessCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 根据code获取name值
     * @param code
     * @return
     */
    public static String getMsg(String code){
        for(BusinessCode businessCode : values()){
            if(businessCode.getCode().equals(code)){
                return businessCode.getMsg();
            }
        }
        return StringUtils.EMPTY;
    }

}
