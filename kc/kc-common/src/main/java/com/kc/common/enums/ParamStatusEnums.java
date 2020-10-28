package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public enum ParamStatusEnums {

    STATUS_0(0, "无效"),
    STATUS_1(1, "有效");


    private Integer status;
    private String name;

    /**
     * @param status
     * @param name
     */
    private ParamStatusEnums(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static final Map<Integer, String> paramStatusEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(ParamStatusEnums.values()).forEach(e->paramStatusEnumsMap.put(e.getStatus(),e.getName()));
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据code获取name值
     * @param status
     * @return
     */
    public static String getName(int status){
        for(ParamStatusEnums paramStatusEnums : values()){
            if(paramStatusEnums.getStatus() == status){
                return paramStatusEnums.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
