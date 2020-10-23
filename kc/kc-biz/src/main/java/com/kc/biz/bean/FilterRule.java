package com.kc.biz.bean;

import java.io.Serializable;

public class FilterRule implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private Integer ruleType;
    private Integer bizType;
    private String filterItem;
    private String filterValue;
    private Long createTime;

    private String ruleTypeDesc;
    private String bizTypeDesc;
    private String filterItemDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getFilterItem() {
        return filterItem;
    }

    public void setFilterItem(String filterItem) {
        this.filterItem = filterItem;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getRuleTypeDesc() {
        return ruleTypeDesc;
    }

    public void setRuleTypeDesc(String ruleTypeDesc) {
        this.ruleTypeDesc = ruleTypeDesc;
    }

    public String getBizTypeDesc() {
        return bizTypeDesc;
    }

    public void setBizTypeDesc(String bizTypeDesc) {
        this.bizTypeDesc = bizTypeDesc;
    }

    public String getFilterItemDesc() {
        return filterItemDesc;
    }

    public void setFilterItemDesc(String filterItemDesc) {
        this.filterItemDesc = filterItemDesc;
    }
}
