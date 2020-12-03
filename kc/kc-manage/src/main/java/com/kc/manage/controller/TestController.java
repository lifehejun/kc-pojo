package com.kc.manage.controller;

import com.kc.biz.bean.TransRecord;
import com.kc.biz.bean.Video;
import com.kc.biz.bean.VipGrade;
import com.kc.biz.service.ITransRecordService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.service.IVipGradeService;
import com.kc.common.enums.VodTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.util.ApiUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController{

    @Autowired
    private ITransRecordService transRecordService;

    @Autowired
    private IVideoService videoService;


    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request,@RequestBody Map<String, Object> params) {
        try {
            /*String userId = String.valueOf(params.get("user
            Integer transType =Integer.valueOf(String.valueOf(params.get("transType")));
            BigDecimal money = new BigDecimal(String.valueOf(params.get("money")));
            String remark = String.valueOf(params.get("remark"));
            int res =  transRecordService.buildTransRecord(userId,transType,money,1, CommConst.TRANS_STATUS_1,remark);*/

            for (int i=1;i<100;i++){

                String url = "http://v.m.yiche.com/cate_53_0_"+i+".html";
                //获得一个和网站的链接，注意是Jsoup的connect
                Connection connect = Jsoup.connect(url);
                //获得该网站的Document对象
                Document document = connect.get();
                int cnt = 1;
                //我们可以通过对Document对象的select方法获得具体的文本内容
                //下面的意思是获得.bool-img-text这个类下的 ul 下的 li
                Elements rootselect = document.select(".pic-txt-video ul li");
                for(Element ele : rootselect){
                    //然后获得a标签里面具体的内容
                    Elements novelname = ele.select("a p");
                    String name  = novelname.text();
                    Elements imgUrl = ele.select("a span img");
                    String img = imgUrl.attr("src");
                    System.out.println("视频名称:"+name+",imgUrl:"+img);
                    Video video = new Video();
                    video.setVodName(name);
                    video.setVodImgUrl(img);
                    video.setStatus(1);
                    video.setVodType(VodTypeEnums.MATCH.getCode());
                    video.setLabelCodeList("VL_4829,VL_8756,VL_7543");
                    video.setUserId("8571912895");
                    video.setAdvFlag(0);
                    video.setVodDesc(name);
                    videoService.insert(video);
                }

            }

            return requestSuccess("success");
        }catch (IOException e){
            logger.error("测试出现异常:{}",e.getMessage());
            return exceptionHandling(e);
        }catch (ApiException e){
        logger.error("测试出现异常:{}",e.getMessage());
        return exceptionHandling(e);
    }

    }


}
