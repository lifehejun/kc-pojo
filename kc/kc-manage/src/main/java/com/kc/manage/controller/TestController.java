package com.kc.manage.controller;

import com.kc.biz.bean.TransRecord;
import com.kc.biz.bean.Video;
import com.kc.biz.bean.VipGrade;
import com.kc.biz.service.ITransRecordService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.service.IVipGradeService;
import com.kc.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController{

    @Autowired
    private ITransRecordService transRecordService;

    @Autowired
    private IVideoService videoService;


    @RequestMapping("/add")
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request,@RequestBody Map<String, Object> params) {
        try {
            /*String userId = String.valueOf(params.get("user
            Integer transType =Integer.valueOf(String.valueOf(params.get("transType")));
            BigDecimal money = new BigDecimal(String.valueOf(params.get("money")));
            String remark = String.valueOf(params.get("remark"));
            int res =  transRecordService.buildTransRecord(userId,transType,money,1, CommConst.TRANS_STATUS_1,remark);*/

            //添加视频
            String userId = String.valueOf(params.get("userId"));
            String vodName = String.valueOf(params.get("vodName"));
            Video video = new Video();
            video.setUserId(userId);
            video.setVodType("match");
            video.setVodName(vodName);
            video.setStatus(1);
            video.setLabelCodeList("match_5001,match_5002,match_5003,match_5004,match_5005");
            video.setVodImgUrl("https://vdposter.bdstatic.com/b89e21de93ed0c2b86713f3f82ad86de.jpeg?x-bce-process=image/resize,m_fill,w_352,h_234/format,f_jpg/quality,Q_100");
            video.setVodPlayUrl("https://vdposter.bdstatic.com/b89e21de93ed0c2b86713f3f82ad86de.mp4");
            int res =  videoService.insert(video);
            return requestSuccess(res);
        }catch (ApiException e){
            logger.error("测试出现异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

}
