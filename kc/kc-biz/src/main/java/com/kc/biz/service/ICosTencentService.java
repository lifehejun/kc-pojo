package com.kc.biz.service;

import com.kc.common.exception.ApiException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;

public interface ICosTencentService {

    Map<String,Object> uploadFileToCOS(MultipartFile file,String moduleName) throws ApiException;

}
