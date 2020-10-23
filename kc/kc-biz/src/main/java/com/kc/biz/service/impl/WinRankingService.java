package com.kc.biz.service.impl;

import com.kc.biz.bean.WinRankingBean;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.WinRankingMapper;
import com.kc.biz.service.IWinRankingService;
import com.kc.common.consts.RedisConst;
import com.kc.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class WinRankingService implements IWinRankingService {


    @Autowired
    private WinRankingMapper winRankingMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public List<WinRankingBean> queryTop10() throws ApiException {

        Map<?, Object> winRankingMap = redisUtil.hget(RedisConst.REDIS_WIN_TOP_KEY);

        if(winRankingMap == null || winRankingMap.size()<=0){

            List<WinRankingBean> resultList = winRankingMapper.queryTop10();

            Map<String,Object> map = new HashMap<String,Object>();
            for (WinRankingBean winRankingBean: resultList){
                map.put(winRankingBean.getUserName(),winRankingBean.getWinContent()+","+ winRankingBean.getMoney()+","+ winRankingBean.getNo());
            }
            //添加到redis缓存
            redisUtil.hsetTimeout(RedisConst.REDIS_WIN_TOP_KEY,map,20);
            return resultList;
        }

       if(null != winRankingMap){

           List<WinRankingBean> winRankingBeanList = new ArrayList<WinRankingBean>();
           for (Map.Entry<?, Object> entry: winRankingMap.entrySet()) {
               WinRankingBean winRankingBean = new WinRankingBean();
               String userName = String.valueOf(entry.getKey());
               String winContent = String.valueOf(entry.getValue());
               winRankingBean.setUserName(userName);
               winRankingBean.setWinContent(winContent.split(",")[0]);
               winRankingBean.setMoney(new BigDecimal(winContent.split(",")[1]));
               winRankingBean.setNo(Integer.valueOf(winContent.split(",")[2]));
               winRankingBeanList.add(winRankingBean);
           }
           return winRankingBeanList;
       }
       return null;
    }
}

