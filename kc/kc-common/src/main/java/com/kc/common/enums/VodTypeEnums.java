package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum  VodTypeEnums {
    MATCH("sidelong", "普通视频(横屏)"),
    ORDINARY("erect", "小视频(竖屏)");


    private String code;
    private String name;

    public static final Map<String, String> vodTypeEnumsMap = new HashMap<>();

    static {
        Arrays.stream(VodTypeEnums.values()).forEach(e->vodTypeEnumsMap.put(e.getCode(),e.getName()));
    }

    /**
     * @param code code
     * @param name name
     */
    private VodTypeEnums(String code, String name) {
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
        for(VodTypeEnums vodTypeEnum : values()){
            if(vodTypeEnum.getCode().equals(code)){
                return vodTypeEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }

}
