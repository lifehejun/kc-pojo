package com.kc.manage.controller;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.ICosTencentService;
import com.kc.common.enums.DictBusTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ICosTencentService cosTencentService;



    @RequestMapping("/cos/uploadFile")
    @ResponseBody
    public Map<String,Object> uploadFileToCOS(@RequestParam(value = "file") MultipartFile file,String moduleName, HttpSession session) {
        try {
            Map<String,Object> result = cosTencentService.uploadFileToCOS(file,moduleName);
            return requestSuccess(result);
        }catch (ApiException e){
            logger.error("上传文件到腾讯cos失败:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }



}