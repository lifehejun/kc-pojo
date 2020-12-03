package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * appid枚举
 */
public enum AdvPositionEnums {
    HOME("home", "app首页广告"),
    START_PAGE("startPage", "app启动页广告"),
    VIDEO_DETAIL("videoDetail", "视频详情页广告"),
    APP_DOWNLOAD("appDownload", "app下载广告")
    ;


    private String code;
    private String name;


    public static final Map<String, String> advPositionEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(AdvPositionEnums.values()).forEach(e->advPositionEnumsMap.put(e.getCode(),e.getName()));
    }

    /**
     * @param code code
     * @param name name
     */
    private AdvPositionEnums(String code, String name) {
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
        if(StringUtils.isBlank(code)){
            return StringUtils.EMPTY;
        }
        for(AdvPositionEnums advPositionEnums : values()){
            if(advPositionEnums.getCode().equals(code)){
                return advPositionEnums.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
