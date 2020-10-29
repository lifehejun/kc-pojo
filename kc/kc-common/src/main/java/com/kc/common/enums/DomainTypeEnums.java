package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum DomainTypeEnums {
    DOMAIN_TYPE_0(0, "访问域名"),
    DOMAIN_TYPE_1(1, "推广域名");

    public static final Map<Integer, String> domainTypeEnumsMap = new HashMap<>();

    static {
        Arrays.stream(DomainTypeEnums.values()).forEach(e->domainTypeEnumsMap.put(e.getCode(),e.getName()));
    }
    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private DomainTypeEnums(int code, String name) {
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
        for(DomainTypeEnums domainTypeEnum : values()){
            if(domainTypeEnum.getCode() == code.intValue()){
                return domainTypeEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
