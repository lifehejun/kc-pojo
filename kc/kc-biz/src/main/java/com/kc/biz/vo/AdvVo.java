package com.kc.biz.vo;


import java.io.Serializable;

public class AdvVo implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String advCode;
    private String advPosition;
    private String advImgUrl;
    private String linkUrl;
    private String appIcon;
    private String appName;
    private String appDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
