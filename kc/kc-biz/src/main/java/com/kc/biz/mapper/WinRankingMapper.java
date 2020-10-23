package com.kc.biz.mapper;

import com.kc.biz.bean.WinRankingBean;

import java.util.List;


/**
 * Created by mark on 2018/4/21.
 */
public interface WinRankingMapper extends BaseMapper<WinRankingBean>{



    List<WinRankingBean> queryTop10();


}
