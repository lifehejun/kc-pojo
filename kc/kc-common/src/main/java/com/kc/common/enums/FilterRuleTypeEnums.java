package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤规则-规则类型枚举
 */
public enum FilterRuleTypeEnums {

    BLACK(1, "黑名单"),
    WHITE(2, "白名单");

    private Integer code;
    private String name;

    public static final Map<Integer, String> filterRuleTypeEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(FilterRuleTypeEnums.values()).forEach(e->filterRuleTypeEnumsMap.put(e.getCode(),e.getName()));
    }

    /**
     * @param code
     * @param name
     */
    private FilterRuleTypeEnums(Integer code, String name) {
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
    public static String getName(Integer code){
        if(null == code){
            return StringUtils.EMPTY;
        }
        for(FilterRuleTypeEnums statusEnum : values()){
            if(statusEnum.getCode() == code.intValue()){
                return statusEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
