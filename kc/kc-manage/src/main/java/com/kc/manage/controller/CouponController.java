package com.kc.manage.controller;

import com.kc.biz.bean.Coupon;
import com.kc.biz.bean.Topic;
import com.kc.biz.service.ICouponService;
import com.kc.biz.service.ITopicService;
import com.kc.common.enums.CouponBusTypeEnums;
import com.kc.common.enums.CouponGiveTypeEnums;
import com.kc.common.enums.CouponTypeEnums;
import com.kc.common.enums.ParamStatusEnums;
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
import java.util.Map;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController{


    @Autowired
    private ICouponService couponService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        request.setAttribute("statusEnums", ParamStatusEnums.paramStatusEnumsMap);
        request.setAttribute("couponBusTypeEnumsMap", CouponBusTypeEnums.couponBusTypeEnumsMap);
        request.setAttribute("couponGiveTypeEnumsMap", CouponGiveTypeEnums.couponGiveTypeEnumsMap);
        request.setAttribute("couponTypeEnumsMap", CouponTypeEnums.couponTypeEnumsMap);
        return "coupon/coupon_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<Coupon> page = couponService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询优惠券信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }




    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            Coupon coupon = couponService.queryById(Long.valueOf(id));
            request.setAttribute("coupon",coupon);
        }
        request.setAttribute("statusEnums", ParamStatusEnums.paramStatusEnumsMap);
        request.setAttribute("couponBusTypeEnumsMap", CouponBusTypeEnums.couponBusTypeEnumsMap);
        request.setAttribute("couponGiveTypeEnumsMap", CouponGiveTypeEnums.couponGiveTypeEnumsMap);
        request.setAttribute("couponTypeEnumsMap", CouponTypeEnums.couponTypeEnumsMap);
        return "coupon/coupon_edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,Coupon coupon) {
        try {
            if(null == coupon.getId()){
                coupon.setCouponCode(GenerationUtil.getCouponCode());
                couponService.insert(coupon);
            }else{
                couponService.updateById(coupon);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存优惠券异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                couponService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            logger.error("删除优惠券异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }



}
