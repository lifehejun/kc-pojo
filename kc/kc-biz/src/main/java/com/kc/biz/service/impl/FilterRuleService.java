package com.kc.biz.service.impl;

import com.kc.biz.bean.FilterRule;
import com.kc.biz.mapper.FilterRuleMapper;
import com.kc.biz.service.IFilterRuleService;
import com.kc.common.enums.FilterBizTypeEnums;
import com.kc.common.enums.FilterItemEnums;
import com.kc.common.enums.FilterRuleTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.ExcelUtil;
import com.kc.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class FilterRuleService implements IFilterRuleService {


    @Autowired
    private FilterRuleMapper filterRuleMapper;

    @Override
    public int insert(FilterRule filterRule) {
        return filterRuleMapper.insert(filterRule);
    }

    @Override
    public int updateById(FilterRule filterRule) {
        return filterRuleMapper.updateById(filterRule);
    }

    @Override
    public int deleteById(Long id) {
        return filterRuleMapper.deleteById(id);
    }

    @Override
    public FilterRule queryById(Long id) {
        return filterRuleMapper.queryById(id);
    }

    @Override
    public Page<FilterRule> queryByPage(Map<String, Object> params) throws ApiException {
        int total = filterRuleMapper.getTotal(params);
        if (total > 0) {
            List<FilterRule> list = filterRuleMapper.queryByPage(params);
            list.forEach(filterRule -> {
                filterRule.setBizTypeDesc(FilterBizTypeEnums.getName(filterRule.getBizType()));
                filterRule.setRuleTypeDesc(FilterRuleTypeEnums.getName(filterRule.getRuleType()));
                filterRule.setFilterItemDesc(FilterItemEnums.getName(filterRule.getFilterItem()));
            });
            return new Page<FilterRule>(list, total);
        }else{
            return new Page<FilterRule>(null, 0);
        }
    }

    @Override
    public List<FilterRule> findList(Map<String,Object> params) {
        return filterRuleMapper.findList(params);
    }

    @Override
    public void importRuleExcel(MultipartFile file) throws ApiException {
        //读取IO流文件
        InputStreamReader input = null;
        try {
            InputStreamReader read = new InputStreamReader( (file.getInputStream()),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(read);

            String strTmp = "";
            List<FilterRule> filterRuleList = new ArrayList<FilterRule>();

            while((strTmp = bufferedReader.readLine())!=null){
                FilterRule filterRule = new FilterRule();
                if(StringUtils.isNotBlank(strTmp)){
                    String[] arr = strTmp.split(",");
                    filterRule.setRuleType(Integer.valueOf(arr[0]));
                    filterRule.setBizType(Integer.valueOf(arr[1]));
                    filterRule.setFilterItem(String.valueOf(arr[2]));
                    filterRule.setFilterValue(String.valueOf(arr[3]));
                }
                filterRuleList.add(filterRule);
            }
            bufferedReader.close();
            //入库
            boolean isSuccess = filterRuleMapper.insertBatch(filterRuleList) >0;
        } catch (Exception e) {
            throw new ApiException(BusinessCode.FILTER_TEMPLATE_IMPORT_FAIL_8004.getCode());
        }
    }

    @Override
    public void checkLoginBlackByPhone(String phone) throws ApiException {
        if(StringUtils.isNotBlank(phone)){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("ruleType",FilterRuleTypeEnums.BLACK.getCode());
            params.put("bizType",FilterBizTypeEnums.LOGIN.getCode());
            params.put("filterItem", FilterItemEnums.PHONE.getCode());
            params.put("filterValue",phone);
            List<FilterRule> filterRules = filterRuleMapper.findList(params);
            if(null != filterRules && filterRules.size()>0){
                throw new ApiException(BusinessCode.FILTER_BLACK_LOGIN_8001.getCode());
            }
        }

    }

    @Override
    public void checkRegBlackByIP(String ip) throws ApiException {
        if(StringUtils.isNotBlank(ip)){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("ruleType",FilterRuleTypeEnums.BLACK.getCode());
            params.put("bizType",FilterBizTypeEnums.REG.getCode());
            params.put("filterItem", FilterItemEnums.IP.getCode());
            params.put("filterValue",ip);
            List<FilterRule> filterRules = filterRuleMapper.findList(params);
            if(null != filterRules && filterRules.size()>0){
                throw new ApiException(BusinessCode.FILTER_BLACK_REG_IP_8002.getCode());
            }
        }
    }

    @Override
    public void checkLoginBlackByIP(String ip) throws ApiException {
        if(StringUtils.isNotBlank(ip)){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("ruleType",FilterRuleTypeEnums.BLACK.getCode());
            params.put("bizType",FilterBizTypeEnums.LOGIN.getCode());
            params.put("filterItem", FilterItemEnums.IP.getCode());
            params.put("filterValue",ip);
            List<FilterRule> filterRules = filterRuleMapper.findList(params);
            if(null != filterRules && filterRules.size()>0){
                throw new ApiException(BusinessCode.FILTER_BLACK_REG_IP_8002.getCode());
            }
        }
    }
}

