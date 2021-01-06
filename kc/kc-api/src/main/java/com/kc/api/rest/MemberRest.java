package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.dto.MemberOrderReqDto;
import com.kc.biz.service.ITransRecordService;
import com.kc.common.base.BaseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberRest extends BaseRest {
    private static final Logger logger = LoggerFactory.getLogger(FollowRest.class);


    @Autowired
    private ITransRecordService transRecordService;
    /**
     * 会员下单
     * @param request
     * @param request tarnsType : 交易类型
     * @param request payType : 支付类型：1：钱包支付，2：在线支付 3：线下支付
     * @param request payConfigId : 支付配置id
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createOrder(HttpServletRequest request, @RequestBody MemberOrderReqDto memberOrderReqDto) {

        try {
            String userId = (String)request.getAttribute("userId");
            transRecordService.createMemberOrder(userId,memberOrderReqDto);
            return requestSuccess("订单创建成功");
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,createOrder()",500);
            return exceptionHandling(e);
        }
    }
}
