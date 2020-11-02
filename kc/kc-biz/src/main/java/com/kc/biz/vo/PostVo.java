package com.kc.biz.vo;

import com.kc.biz.bean.PostImage;
import com.kc.biz.bean.PostVideo;

import java.math.BigDecimal;
import java.util.List;

public class PostVo {

    private Long id;
    private String userId;
    private String postTitle;
    private Integer likeNum;
    private Integer viewNum;
    private Integer commentNum;
    private BigDecimal rewardAmount;
    private String publishTime;

    private String topicCodeList; //标签集合
    private List<String> topicTitleList; //标签名称集合
    private List<PostImage> postImageList; //图片集合，最多9张
    private String postImages; //图片集合，最多9张,用逗号隔开
    PostVideo postVideo;
    private String address;
    private String headUrl;
    private String userName;
    private Integer grade;

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

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
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

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getTopicCodeList() {
        return topicCodeList;
    }

    public void setTopicCodeList(String topicCodeList) {
        this.topicCodeList = topicCodeList;
    }

    public List<PostImage> getPostImageList() {
        return postImageList;
    }

    public void setPostImageList(List<PostImage> postImageList) {
        this.postImageList = postImageList;
    }

    public PostVideo getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(PostVideo postVideo) {
        this.postVideo = postVideo;
    }

    public List<String> getTopicTitleList() {
        return topicTitleList;
    }

    public void setTopicTitleList(List<String> topicTitleList) {
        this.topicTitleList = topicTitleList;
    }

    public String getPostImages() {
        return postImages;
    }

    public void setPostImages(String postImages) {
        this.postImages = postImages;
    }
}
