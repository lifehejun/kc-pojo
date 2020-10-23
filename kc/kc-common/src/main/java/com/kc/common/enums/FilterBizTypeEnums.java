package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤规则-业务类型枚举
 */
public enum FilterBizTypeEnums {

    LOGIN(100, "登录"),
    REG(101, "注册");

    private Integer code;
    private String name;


    public static final Map<Integer, String> filterBizTypeEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(FilterBizTypeEnums.values()).forEach(e->filterBizTypeEnumsMap.put(e.getCode(),e.getName()));
    }

    /**
     * @param code
     * @param name
     */
    private FilterBizTypeEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
    public static String getName(int code){
        for(FilterBizTypeEnums statusEnum : values()){
            if(statusEnum.getCode() == code){
                return statusEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
