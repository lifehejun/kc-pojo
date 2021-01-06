package com.kc.biz.dto;

import java.io.Serializable;

public class MemberOrderReqDto implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Integer transType;
    private Integer payType;
    private Integer payConfigId;
    private String subServiceId;

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayConfigId() {
        return payConfigId;
    }

    public void setPayConfigId(Integer payConfigId) {
        this.payConfigId = payConfigId;
    }

    public String getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(String subServiceId) {
        this.subServiceId = subServiceId;
    }
}
