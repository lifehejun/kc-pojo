package com.kc.api.rest;

import com.kc.biz.bean.Post;
import com.kc.biz.bean.PostImage;
import com.kc.biz.bean.Video;
import com.kc.biz.service.IAdvService;
import com.kc.biz.service.IPostService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.vo.AdvShowVo;
import com.kc.common.base.BaseRest;
import com.kc.common.enums.VodTypeEnums;
import com.kc.common.util.ApiUtils;
import com.kc.common.util.DateTools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(TestRest.class);
    @Autowired
    private IVideoService videoService;

    @Autowired
    private IPostService postService;



    @RequestMapping(value = "/run", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> run(HttpServletRequest request) {
        try {
            for (int i=1;i<100;i++){
                String url = "http://v.m.yiche.com/cate_47_0_"+i+".html";
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
                   /* Elements labelA = ele.select("a");
                    String uri ="http:"+labelA.attr("href");
                    Connection connect1 = Jsoup.connect(uri);
                    //获得该网站的Document对象
                    Document document1 = connect1.get();

                    Elements videoDiv = document1.select(".vjs-tech");
                    String videoUrl = videoDiv.attr("src");*/

                    Elements novelname = ele.select("a p");
                    String name  = novelname.text();
                    Elements imgUrl = ele.select("a span img");
                    String img = "http:"+imgUrl.attr("src");
                    System.out.println("视频名称:"+name+",imgUrl:"+img);
                    Video video = new Video();
                    video.setVodName(name);
                    video.setVodImgUrl(img);
                    //video.setVodPlayUrl(videoUrl);
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
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,run()",500);
            return exceptionHandling(e);
        }
    }



    @RequestMapping(value = "/addPost",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> addPost(HttpServletRequest request,@RequestBody Map<String, Object> params) {
        try {

            //已经调用1000次了
            for (int i=1000;i<10000;i++){
                String result = ApiUtils.getResult(String.valueOf(i));
                JSONObject  data = JSONObject.fromObject(result);
                JSONObject jsonResult = (JSONObject)data.getJSONObject("result");
                JSONArray jsonArray = jsonResult.getJSONArray("list");
                for(int a=0;a<jsonArray.size();a++){
                    JSONObject object =jsonArray.getJSONObject(a);
                    String content = object.getString("content");
                    String pic = object.getString("pic");
                    Post post = new Post();
                    post.setUserId("8571912895");
                    post.setPostTitle(content);
                    post.setTopicCodeList("TC_7814,TC_9614,TC_9300");
                    post.setPublishTime(DateTools.getLongCurrTime());
                    post.setStatus(1);
                    post.setCommentNum(111);

                    int res = postService.insert(post);
                    if(res>0){
                        PostImage postImage =new PostImage();
                        postImage.setPostId(post.getId());
                        postImage.setImgUrl(pic);
                        postImage.setSize(11);
                        postService.insertPostImage(postImage);
                    }

                }
            }

            return requestSuccess("success");
        }catch (Exception e){
            logger.error("测试出现异常:{}",e.getMessage());
            return exceptionHandling(e);
        }

    }



    @RequestMapping(value = "/addBDJ",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> addBDJ(HttpServletRequest request) {
        try {

            List<String> urlList = new ArrayList<>();
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/0-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1605863174-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1604975189-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1604634458-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1604546880-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1604546842-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1604478578-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1604478472-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1603792060-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1603771893-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1603444634-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1603191138-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1602840904-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1602758900-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1602322061-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1602227266-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1602227184-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1602130191-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1601188005-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1601026086-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1600855759-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1600771776-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1600687801-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1600248416-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1600160069-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1599793963-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1599728718-20.json");
            urlList.add("http://d.api.budejie.com/topic/forum/58240/1/new/budejie-android-8.2.9/1599554906-20.json");

            //已经调用1000次了
            for (int i=0;i<urlList.size();i++){
                String result = ApiUtils.getBDJResult(urlList.get(i));
                if(StringUtils.isBlank(result)){
                    continue;
                }
                JSONObject  data = JSONObject.fromObject(result);
                JSONArray jsonArray = data.getJSONArray("list");
                for(int a=0;a<jsonArray.size();a++){
                    JSONObject object =jsonArray.getJSONObject(a);

                    JSONObject imgObject = (JSONObject)object.getJSONObject("image");

                    if(null == imgObject || imgObject.size()<=0){
                        continue;
                    }
                    String text = object.getString("text");
                    JSONArray bigImgObject = imgObject.getJSONArray("big");
                    if(null == bigImgObject || bigImgObject.size()<=0){
                        continue;
                    }
                    Post post = new Post();
                    post.setUserId("8571912895");
                    post.setPostTitle(text);
                    post.setTopicCodeList("TC_7814,TC_9614,TC_9300");
                    post.setPublishTime(DateTools.getLongCurrTime());
                    post.setStatus(1);
                    post.setCommentNum(111);

                    int res = postService.insert(post);
                    if(res>0){
                        for (int c=0;c<bigImgObject.size();c++){
                            String imgUrl = (String)bigImgObject.get(c);
                            PostImage postImage =new PostImage();
                            postImage.setPostId(post.getId());
                            postImage.setImgUrl(imgUrl);
                            postImage.setSize(11);
                            postService.insertPostImage(postImage);
                        }

                    }

                }
            }

            return requestSuccess("success");
        }catch (Exception e){
            logger.error("测试出现异常:{}",e.getMessage());
            return exceptionHandling(e);
        }

    }

}
