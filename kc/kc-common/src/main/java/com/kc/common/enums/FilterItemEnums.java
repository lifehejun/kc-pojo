package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤规则-过滤属性枚举
 */
public enum FilterItemEnums {

    IP("ip", "IP地址"),
    PHONE("phone", "手机号");

    private String code;
    private String name;

    public static final Map<String, String> filterItemEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(FilterItemEnums.values()).forEach(e->filterItemEnumsMap.put(e.getCode(),e.getName()));
    }
    /**
     * @param code
     * @param name
     */
    private FilterItemEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
    public static String getName(String code){
        for(FilterItemEnums statusEnum : values()){
            if(statusEnum.getCode().equals(code)){
                return statusEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
