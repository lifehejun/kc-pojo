package com.kc.biz.bean;

import java.io.Serializable;

public class Post implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String userId;
    private String topicCodeList;
    private String postTitle;
    private Integer status;
    private Integer likeNum;
    private Integer viewNum;
    private Integer commentNum;
    private Integer rewardAmount;
    private Long createTime;
    private Long publishTime;

    private String phone;
    private String topicTitleList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopicCodeList() {
        return topicCodeList;
    }

    public void setTopicCodeList(String topicCodeList) {
        this.topicCodeList = topicCodeList;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTopicTitleList() {
        return topicTitleList;
    }

    public void setTopicTitleList(String topicTitleList) {
        this.topicTitleList = topicTitleList;
    }
}
