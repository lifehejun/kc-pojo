package com.kc.biz.service;


import com.kc.biz.vo.charts.ChartsRegVo;
import com.kc.common.exception.ApiException;

/**
 * Created by timi on 2018/4/21.
 */
public interface IChartService {

    ChartsRegVo getChartsReg() throws ApiException; //获取注册统计图数据

}
