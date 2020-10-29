package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum UserTypeEnums {
    USER_TYPE_NORMAL_1(1, "普通用户"),
    USER_TYPE_TEST_2(2, "内部测试用户");

    public static final Map<Integer, String> userTypeEnumsMap = new HashMap<>();

    static {
        Arrays.stream(UserTypeEnums.values()).forEach(e->userTypeEnumsMap.put(e.getCode(),e.getName()));
    }
    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private UserTypeEnums(int code, String name) {
        this.code = code;
        this.name = name;
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

    /**
     * 根据code获取name值
     * @param code
     * @return
     */
    public static String getName(Integer code){
        if(null == code){
            return StringUtils.EMPTY;
        }
        for(UserTypeEnums userTypeEnum : values()){
            if(userTypeEnum.getCode() == code.intValue()){
                return userTypeEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
