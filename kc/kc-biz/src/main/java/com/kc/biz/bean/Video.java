package com.kc.biz.bean;

import java.io.Serializable;

public class Video implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String userId;
    private String vodName;
    private String vodDesc;
    private String vodType;
    private String vodImgUrl;
    private String vodPlayUrl;
    private Integer advFlag;
    private String labelCodeList;
    private Integer likeNum;
    private Integer commentNum;
    private Integer status;
    private Long createTime;

    private String labelNameList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVodName() {
        return vodName;
    }

    public void setVodName(String vodName) {
        this.vodName = vodName;
    }

    public String getVodType() {
        return vodType;
    }

    public void setVodType(String vodType) {
        this.vodType = vodType;
    }

    public String getVodImgUrl() {
        return vodImgUrl;
    }

    public void setVodImgUrl(String vodImgUrl) {
        this.vodImgUrl = vodImgUrl;
    }

    public Integer getAdvFlag() {
        return advFlag;
    }

    public void setAdvFlag(Integer advFlag) {
        this.advFlag = advFlag;
    }

    public String getVodPlayUrl() {
        return vodPlayUrl;
    }

    public void setVodPlayUrl(String vodPlayUrl) {
        this.vodPlayUrl = vodPlayUrl;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLabelCodeList() {
        return labelCodeList;
    }

    public void setLabelCodeList(String labelCodeList) {
        this.labelCodeList = labelCodeList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLabelNameList() {
        return labelNameList;
    }

    public void setLabelNameList(String labelNameList) {
        this.labelNameList = labelNameList;
    }

    public String getVodDesc() {
        return vodDesc;
    }

    public void setVodDesc(String vodDesc) {
        this.vodDesc = vodDesc;
    }
}
