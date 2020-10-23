package com.kc.api.rest;

import com.kc.biz.bean.VideoLabel;
import com.kc.biz.service.IVideoLabelService;
import com.kc.common.base.BaseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/label")
public class LabelRest extends BaseRest {
    private static final Logger logger = LoggerFactory.getLogger(FollowRest.class);

    @Autowired
    private IVideoLabelService videoLabelService;

    /**
     * 关注
     * @param request
     * @param request vodType : 视频类型code
     * @return
     */
    @RequestMapping(value = "/findVideoLabelByType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findLabelByType(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            List<VideoLabel> list = videoLabelService.findLabelByType(params);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findLabelByType()",500);
            return exceptionHandling(e);
        }
    }
}
