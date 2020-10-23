package com.kc.biz.bean;

import java.io.Serializable;

public class ForbidWord implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String keyWord;
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


}
