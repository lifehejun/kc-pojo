package com.kc.manage.controller;

import com.kc.biz.bean.*;
import com.kc.biz.service.IFilterRuleService;
import com.kc.biz.service.IPostService;
import com.kc.biz.service.IUserService;
import com.kc.common.enums.FilterBizTypeEnums;
import com.kc.common.enums.FilterItemEnums;
import com.kc.common.enums.FilterRuleTypeEnums;
import com.kc.common.enums.UserTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/filterRule")
public class FilterRuleController extends BaseController{


    @Autowired
    private IUserService userService;
    @Autowired
    private IFilterRuleService filterRuleService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        request.setAttribute("filterBizTypeMap", FilterBizTypeEnums.filterBizTypeEnumsMap);
        request.setAttribute("filterRuleTypeMap", FilterRuleTypeEnums.filterRuleTypeEnumsMap);
        request.setAttribute("filterItemEnumsMap", FilterItemEnums.filterItemEnumsMap);

        return "filter/rule_list";
    }



    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<FilterRule> page = filterRuleService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            FilterRule filterRule = filterRuleService.queryById(Long.valueOf(id));
            request.setAttribute("filterRule",filterRule);
        }
        request.setAttribute("filterBizTypeMap", FilterBizTypeEnums.filterBizTypeEnumsMap);
        request.setAttribute("filterRuleTypeMap", FilterRuleTypeEnums.filterRuleTypeEnumsMap);
        request.setAttribute("filterItemEnumsMap", FilterItemEnums.filterItemEnumsMap);

        return "filter/rule_edit";
    }

    @RequestMapping("/openExcelDialog")
    public String openExcelDialog(HttpServletRequest request, HttpSession session,String id) {
        return "filter/excel_dialog";
    }


    /**
     * 导入规则
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/importRuleExcel")
    @ResponseBody
    public Map<String,Object> importRuleExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request, String id) {
        try {
            filterRuleService.importRuleExcel(file);
            return requestSuccess("导入成功");
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                filterRuleService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,FilterRule filterRule) {
        try {
            if(null == filterRule.getId()){
                filterRuleService.insert(filterRule);
            }else{
                filterRuleService.updateById(filterRule);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }

}
