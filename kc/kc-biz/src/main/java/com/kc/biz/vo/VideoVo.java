package com.kc.biz.vo;

import java.util.List;

public class VideoVo {

    private Long id; //视频id
    //private String userId; //用户id
    private String vodName; //视频名称
    private String vodImgUrl; //视频封面图片路径
    private String vodPlayUrl; //视频播放地址
    private Integer likeNum; //喜欢，点赞量
    private Integer commentNum;//评论量
    private Integer status; //视频状态
    private String createTimeStr;
    private String labelCodeList;
    private List<String> labelNameList; //视频标签名称


    /*public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public String getVodName() {
        return vodName;
    }

    public void setVodName(String vodName) {
        this.vodName = vodName;
    }

    public String getVodImgUrl() {
        return vodImgUrl;
    }

    public void setVodImgUrl(String vodImgUrl) {
        this.vodImgUrl = vodImgUrl;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public List<String> getLabelNameList() {
        return labelNameList;
    }

    public void setLabelNameList(List<String> labelNameList) {
        this.labelNameList = labelNameList;
    }

    public String getLabelCodeList() {
        return labelCodeList;
    }

    public void setLabelCodeList(String labelCodeList) {
        this.labelCodeList = labelCodeList;
    }
}
