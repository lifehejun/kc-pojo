package com.kc.biz.service.impl;

import com.kc.biz.bean.Domain;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.DomainMapper;
import com.kc.biz.service.IDomainService;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
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
public class DomainServiceImpl implements IDomainService {



    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DomainMapper domainMapper;


    @Override
    public int insert(Domain domain) {
        return domainMapper.insert(domain);
    }

    @Override
    public int updateById(Domain domain) {
        int res = domainMapper.updateById(domain);
        return res;
    }

    @Override
    public int deleteById(Long id) {
        return domainMapper.deleteById(id);
    }

    @Override
    public Domain queryById(Long id) {
        return domainMapper.queryById(id);
    }

    @Override
    public Page<Domain> queryByPage(Map<String, Object> params) throws ApiException {
        int total = domainMapper.getTotal(params);
        if (total > 0) {
            List<Domain> list = domainMapper.queryByPage(params);
            return new Page<Domain>(list, total);
        }else{
            return new Page<Domain>(null, 0);
        }
    }

    @Override
    public List<Domain> findList() {
        return domainMapper.findList();
    }

    @Override
    public List<Domain> findListByType(String domainType) {
        return domainMapper.findListByType(domainType);
    }



}

