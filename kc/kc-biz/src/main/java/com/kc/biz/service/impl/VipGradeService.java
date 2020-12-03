package com.kc.biz.service.impl;

import com.kc.biz.bean.VipGrade;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.VipGradeMapper;
import com.kc.biz.service.IVipGradeService;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
    public int insert(VipGrade vipGrade) {
        return vipGradeMapper.insert(vipGrade);
    }

    @Override
    public int updateById(VipGrade vipGrade) {
        return vipGradeMapper.updateById(vipGrade);
    }

    @Override
    public int deleteById(Long id) {
        return vipGradeMapper.deleteById(id);
    }

    @Override
    public VipGrade queryById(Long id) {
        return vipGradeMapper.queryById(id);
    }

    @Override
    public  List<VipGrade> queryByGrade(Integer grade) {
        Map<String,Object> params = new HashMap<>();
        params.put("grade",grade);
        List<VipGrade> list = vipGradeMapper.findList(params);
        return list;
    }

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

