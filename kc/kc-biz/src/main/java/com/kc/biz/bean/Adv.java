package com.kc.biz.bean;

import java.io.Serializable;

public class Adv implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String advCustomer;
    private String advCode;
    private String advPosition;
    private String advImgUrl;
    private String linkUrl;
    private String appIcon;
    private String appName;
    private String appDesc;
    private Integer status;
    private Long startTime;
    private Long endTime;
    private Long createTime;

    private String advPositionDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdvCustomer() {
        return advCustomer;
    }

    public void setAdvCustomer(String advCustomer) {
        this.advCustomer = advCustomer;
    }

    public String getAdvCode() {
        return advCode;
    }

    public void setAdvCode(String advCode) {
        this.advCode = advCode;
    }

    public String getAdvPosition() {
        return advPosition;
    }

    public void setAdvPosition(String advPosition) {
        this.advPosition = advPosition;
    }

    public String getAdvImgUrl() {
        return advImgUrl;
    }

    public void setAdvImgUrl(String advImgUrl) {
        this.advImgUrl = advImgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getAdvPositionDesc() {
        return advPositionDesc;
    }

    public void setAdvPositionDesc(String advPositionDesc) {
        this.advPositionDesc = advPositionDesc;
    }
}
