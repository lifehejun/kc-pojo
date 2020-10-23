package com.kc.biz.service.impl;

import com.kc.biz.bean.SpreadDetail;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.SpreadDetailMapper;
import com.kc.biz.service.ISpreadDetailService;
import com.kc.biz.vo.SpreadDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class SpreadDetailService implements ISpreadDetailService {

    @Autowired
    private SpreadDetailMapper spreadDetailMapper;

    @Async("taskExecutor")
    @Override
    public void syncSpreadDetail(SpreadDetailVo vo) {
        SpreadDetail spreadDetail = new SpreadDetail();
        BeanUtils.copyProperties(vo,spreadDetail);
        spreadDetailMapper.insert(spreadDetail);
    }

}

