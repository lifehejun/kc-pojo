package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

public enum  GradeEnums {
    GRADE_0(0, "普通用户","普通用户"),
    GRADE_1(1, "白银","白银周卡"),
    GRADE_2(2, "黄金","黄金月卡"),
    GRADE_3(3, "钻石","钻石年卡"),
    GRADE_4(4, "至尊","至尊永久卡");


    private int code;
    private String name;
    private String vipName;

    /**
     * @param code code
     * @param name name
     */
    private GradeEnums(int code, String name,String vipName) {
        this.code = code;
        this.name = name;
        this.vipName = vipName;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    /**
     * 根据code获取name值
     * @param code
     * @return
     */
    public static String getName(Integer code){
        if(null == code){
            return StringUtils.EMPTY;
        }
        for(GradeEnums statusEnum : values()){
            if(statusEnum.getCode() == code.intValue()){
                return statusEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据code获取name值
     * @param code
     * @return
     */
    public static String getVipName(Integer code){
        if(null == code){
            return StringUtils.EMPTY;
        }
        for(GradeEnums statusEnum : values()){
            if(statusEnum.getCode() == code.intValue()){
                return statusEnum.getVipName();
            }
        }
        return StringUtils.EMPTY;
    }
}
