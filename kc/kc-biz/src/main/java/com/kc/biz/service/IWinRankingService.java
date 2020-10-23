package com.kc.biz.service;

import com.kc.biz.bean.WinRankingBean;
import com.kc.common.exception.ApiException;

import java.util.List;

/**
 * Created by timi on 2018/4/21.
 */
public interface IWinRankingService {



    List<WinRankingBean> queryTop10() throws ApiException;





}
