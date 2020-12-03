package com.kc.api.rest;

import com.kc.biz.service.IAdvService;
import com.kc.biz.service.ITopicService;
import com.kc.biz.vo.AdvShowVo;
import com.kc.biz.vo.TopicShowVo;
import com.kc.common.base.BaseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/adv")
public class AdvRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(AdvRest.class);
    @Autowired
    private IAdvService advService;

    /**
     * 查询app广告
     * @param request
     * @return
     */
    @RequestMapping(value = "/findAppAdv", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findAppAdv(HttpServletRequest request) {
        try {
            AdvShowVo advShowVo =  advService.findAppAdv();
            return requestSuccess(advShowVo);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findAppAdv()",500);
            return exceptionHandling(e);
        }
    }

}
