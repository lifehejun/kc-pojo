package com.kc.api.rest;

import com.kc.biz.bean.Topic;
import com.kc.biz.service.ITopicService;
import com.kc.biz.vo.TopicShowVo;
import com.kc.common.base.BaseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/topic")
public class TopicRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(TopicRest.class);
    @Autowired
    private ITopicService topicService;

    /**
     * 查询话题
     * @param request
     * @return
     */
    @RequestMapping(value = "/findTopicList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findTopicList(HttpServletRequest request) {
        try {
            List<TopicShowVo> list =  topicService.findListByRedis();
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findTopicList()",500);
            return exceptionHandling(e);
        }
    }

}
