package com.kc.biz.service.impl;

import com.kc.biz.bean.Video;
import com.kc.biz.bean.VideoLabel;
import com.kc.biz.bean.VipGrade;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.VideoMapper;
import com.kc.biz.mapper.VipGradeMapper;
import com.kc.biz.service.IVideoService;
import com.kc.biz.service.IVipGradeService;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class VipGradeService implements IVipGradeService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VipGradeMapper vipGradeMapper;


    @Override
    public Page<VipGrade> queryByPage(Map<String, Object> params) throws ApiException {
        int total = vipGradeMapper.getTotal(params);
        if (total > 0) {
            List<VipGrade> list = vipGradeMapper.queryByPage(params);
            return new Page<VipGrade>(list, total);
        }else{
            return new Page<VipGrade>(null, 0);
        }
    }
}

