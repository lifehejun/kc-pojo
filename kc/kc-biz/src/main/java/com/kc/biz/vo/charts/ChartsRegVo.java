package com.kc.biz.vo.charts;

import java.util.List;

//注册统计图数据模型
public class ChartsRegVo {
    private List<String> days;
    private List<String> data;

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
