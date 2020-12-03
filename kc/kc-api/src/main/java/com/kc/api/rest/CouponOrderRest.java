package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.service.ICouponOrderService;
import com.kc.biz.service.ICouponService;
import com.kc.biz.vo.CouponOrderVo;
import com.kc.biz.vo.CouponVo;
import com.kc.common.base.BaseRest;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.DateTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/couponOrder")
public class CouponOrderRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(CouponOrderRest.class);
    @Autowired
    private ICouponOrderService couponOrderService;

    /**
     * 领取优惠券
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/receiveCoupon", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> receiveCoupon(HttpServletRequest request,@RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            String couponCode = params.get("couponCode");
            if(StringUtils.isBlank(couponCode)){
                return requestError(BusinessCode.COUPON_ID_NULL_8301.getCode());
            }
            couponOrderService.receiveCoupon(userId,couponCode);
            return requestSuccess("领取成功");
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findMyCouponList()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 查询我领取的优惠券
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/findMyCouponList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findMyCouponList(HttpServletRequest request,@RequestBody Map<String, Object> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            List<CouponOrderVo> couponOrderVos =  couponOrderService.findMyCouponList(params);
            return requestSuccess(couponOrderVos);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findMyCouponList()",500);
            return exceptionHandling(e);
        }
    }

}
