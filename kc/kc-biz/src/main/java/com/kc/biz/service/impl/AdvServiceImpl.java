package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kc.biz.bean.Adv;
import com.kc.biz.bean.Topic;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.AdvMapper;
import com.kc.biz.mapper.TopicMapper;
import com.kc.biz.service.IAdvService;
import com.kc.biz.service.ITopicService;
import com.kc.biz.vo.AdvShowVo;
import com.kc.biz.vo.AdvVo;
import com.kc.biz.vo.TopicShowVo;
import com.kc.common.enums.AdvPositionEnums;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.GenerationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class AdvServiceImpl implements IAdvService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AdvMapper advMapper;



    @Override
    public int insert(Adv adv) {
        int res = advMapper.insert(adv);
        return res;
    }

    @Override
    public int updateById(Adv adv) {
        int res = advMapper.updateById(adv);
        return res;
    }

    @Override
    public int deleteById(Long id) {
        return advMapper.deleteById(id);
    }

    @Override
    public Adv queryById(Long id) {
        return advMapper.queryById(id);
    }

    @Override
    public Page<Adv> queryByPage(Map<String, Object> params) throws ApiException {
        int total = advMapper.getTotal(params);
        if (total > 0) {
            List<Adv> list = advMapper.queryByPage(params);
            list.forEach(adv -> {
                adv.setAdvPositionDesc(AdvPositionEnums.getName(adv.getAdvPosition()));
            });
            return new Page<Adv>(list, total);
        }else{
            return new Page<Adv>(null, 0);
        }
    }


    @Override
    public List<Adv> findList() {
        return advMapper.findList();
    }

    @Override
    public AdvShowVo findAppAdv() throws ApiException {
        AdvShowVo advShowVo = new AdvShowVo();
        List<Adv> advList = advMapper.findList();
        if(null == advList || advList.size()<=0){
            return null;
        }

        Map<String, List<Adv>> advMapList = advList.stream().collect(Collectors.groupingBy(Adv::getAdvPosition));
        //app启动页广告
        List<Adv> startPageAdvList = advMapList.get(AdvPositionEnums.START_PAGE.getCode());
        if(null != startPageAdvList && startPageAdvList.size()>0){
            Random random = new Random();
            //随机取一个广告
            int n = random.nextInt(startPageAdvList.size());
            Adv adv = startPageAdvList.get(n);
            AdvVo advVo = new AdvVo();
            BeanUtils.copyProperties(adv,advVo);
            advShowVo.setStartPageAdv(advVo);
        }

        //app首页广告
        List<Adv> homeAdvList = advMapList.get(AdvPositionEnums.HOME.getCode());
        List<AdvVo> homeAdvVoList = new ArrayList<>();
        if(null != homeAdvList && homeAdvList.size()>0){
            for (Adv adv :homeAdvList){
                AdvVo advVo = new AdvVo();
                BeanUtils.copyProperties(adv,advVo);
                homeAdvVoList.add(advVo);
            }
            advShowVo.setHomeAdvList(homeAdvVoList);
        }

        //详情页广告
        List<Adv> videoDetailAdvList = advMapList.get(AdvPositionEnums.VIDEO_DETAIL.getCode());
        if(null != videoDetailAdvList && videoDetailAdvList.size()>0){
            Adv adv = videoDetailAdvList.get(0); //取一个
            AdvVo advVo = new AdvVo();
            BeanUtils.copyProperties(adv,advVo);
            advShowVo.setVideoDetailAdv(advVo);
        }

        //详情页广告
        List<Adv> downloadAdvList = advMapList.get(AdvPositionEnums.APP_DOWNLOAD.getCode());
        if(null != downloadAdvList && downloadAdvList.size()>0){
            Adv adv = downloadAdvList.get(0); //取一个
            AdvVo advVo = new AdvVo();
            BeanUtils.copyProperties(adv,advVo);
            advShowVo.setAppDownloadAdv(advVo);
        }
        return advShowVo;
    }

}

