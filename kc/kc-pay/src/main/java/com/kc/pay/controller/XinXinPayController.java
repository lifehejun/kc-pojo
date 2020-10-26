package com.kc.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api-pay/xinxinPay")
public class XinXinPayController {

    @RequestMapping(value = "/getParams", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> returnFollow(HttpServletRequest request) {
        Map<String,Object> d = new HashMap<>();
        d.put("payUrl","https://alipay.com");
        d.put("code","00");
        return d;
    }
}
