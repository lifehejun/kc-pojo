package com.kc.api.rest;

import com.kc.biz.bean.WinRankingBean;
import com.kc.biz.service.IWinRankingService;
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
@RequestMapping("/api/index")
public class IndexRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(IndexRest.class);

    @Autowired
    private IWinRankingService winRankingService;

    /**
     * 首页盈利排行榜
     * @param request
     * @return
     */
    @RequestMapping(value = "/winTop10", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> winTop10(HttpServletRequest request) {
        try {
            List<WinRankingBean> winRankingBeanList = winRankingService.queryTop10();
            return requestSuccess(winRankingBeanList);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,winTop10()",500);
            return exceptionHandling(e);
        }
    }
}
