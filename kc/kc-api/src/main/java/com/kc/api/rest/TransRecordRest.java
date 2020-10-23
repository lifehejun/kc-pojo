package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.bean.TransRecord;
import com.kc.biz.service.ITransRecordService;
import com.kc.common.base.BaseRest;
import com.kc.common.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/trans")
public class TransRecordRest extends BaseRest {


    private static final Logger logger = LoggerFactory.getLogger(TransRecordRest.class);

    @Autowired
    private ITransRecordService transRecordService;


    @UserLoginToken
    @RequestMapping(value = "/queryTransRecord", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryTransRecord(HttpServletRequest request, @RequestBody Map<String, String> params) {

        try {

            Map<String,Object> transParams = new HashMap<String,Object>();
            logger.info("queryTransRecord params : {}",params.toString());
            String userId = (String)request.getAttribute("userId");

            transParams.put("days",params.get("days"));
            transParams.put("transType", StringUtils.isNotBlank(params.get("transType"))?Integer.valueOf(params.get("transType")):null);
            transParams.put("userId",userId);
            Page<TransRecord> list = transRecordService.queryTrans(transParams);
            return requestSuccess(list);
        } catch (Exception ex) {
            logger.info("ERROR:{}系统异常,queryTransRecord()",500);
            return exceptionHandling(ex);
        }

    }


}
