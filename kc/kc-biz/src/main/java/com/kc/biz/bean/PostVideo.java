package com.kc.biz.bean;

import java.io.Serializable;

public class PostVideo implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private Long postId;
    private String vodPlayUrl;
    private Integer vodTime;
    private Integer size;
    private Long createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getVodPlayUrl() {
        return vodPlayUrl;
    }

    public void setVodPlayUrl(String vodPlayUrl) {
        this.vodPlayUrl = vodPlayUrl;
    }

    public Integer getVodTime() {
        return vodTime;
    }

    public void setVodTime(Integer vodTime) {
        this.vodTime = vodTime;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
