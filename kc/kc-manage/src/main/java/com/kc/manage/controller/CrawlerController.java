package com.kc.manage.controller;

import com.kc.biz.bean.Video;
import com.kc.biz.service.IVideoService;
import com.kc.common.enums.VodTypeEnums;
import com.kc.common.exception.ApiException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/crawler")
public class CrawlerController extends BaseController{


    @Autowired
    private IVideoService videoService;




    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request) {
        try {


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
                    System.out.println("书名:"+name+",imgUrl:"+img);

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

            return requestSuccess("sucess");
        }catch (IOException e){
            return exceptionHandling(e);
        }catch (ApiException e){
            logger.error("查询基础配置信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }



}