package com.kc.manage.controller;

import com.kc.biz.bean.VipGrade;
import com.kc.biz.service.IVipGradeService;
import com.kc.common.enums.GradeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.GenerationUtil;
import com.kc.common.util.api.ReqParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vipGrade")
public class VipGradeController extends BaseController{

    @Autowired
    private IVipGradeService vipGradeService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        request.setAttribute("vipGradeEnumsMap", GradeEnums.vipGradeEnumsMap);
        return "vip/grade_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<VipGrade> page = vipGradeService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询vip等级信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            VipGrade vipGrade = vipGradeService.queryById(Long.valueOf(id));
            request.setAttribute("vipGrade",vipGrade);
        }
        request.setAttribute("vipGradeEnumsMap", GradeEnums.vipGradeEnumsMap);
        return "vip/grade_edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,VipGrade vipGrade) {
        try {
            if(null == vipGrade.getId()){
                //校验是否添加相同的VIP等级
                List<VipGrade> list = vipGradeService.queryByGrade(vipGrade.getGrade());
                if(null != list && list.size()>0){
                    return requestError("已存在该等级的会员信息,请重新选择");
                }
                vipGrade.setVipCode(GenerationUtil.getVipGradeCode());
                vipGradeService.insert(vipGrade);
            }else{
                vipGradeService.updateById(vipGrade);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存VIP等级信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }
}
