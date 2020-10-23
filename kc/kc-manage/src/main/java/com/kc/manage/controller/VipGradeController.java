package com.kc.manage.controller;

import com.kc.biz.bean.VipGrade;
import com.kc.biz.service.IVipGradeService;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/vipGrade")
public class VipGradeController extends BaseController{

    @Autowired
    private IVipGradeService vipGradeService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
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
}
