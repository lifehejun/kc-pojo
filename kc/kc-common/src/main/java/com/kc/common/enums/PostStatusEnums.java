package com.kc.common.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public enum PostStatusEnums {

    POST_STATUS_0(0, "待审核"),
    POST_STATUS_1(1, "审核通过");


    private Integer status;
    private String name;

    public static final Map<Integer, String> postStatusEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(PostStatusEnums.values()).forEach(e->postStatusEnumsMap.put(e.getStatus(),e.getName()));
    }

    /**
     * @param status
     * @param name
     */
    private PostStatusEnums(Integer status, String name) {
        this.status = status;
        this.name = name;
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
}
