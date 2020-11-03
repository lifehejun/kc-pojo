package com.kc.biz.service.impl;

import com.kc.biz.cache.RedisUtil;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.ICosTencentService;
import com.kc.common.consts.BusConfigConst;
import com.kc.common.consts.RedisConst;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.PropertiesUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@Transactional
public class CosTencentServiceImpl implements ICosTencentService {

    private final Logger logger = LoggerFactory.getLogger(CosTencentServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IBusConfigService busConfigService;

    private static final String fileDomain = PropertiesUtil.getProperty("tencent.could.cos.fileDomain");
    private static final String accessKey = PropertiesUtil.getProperty("tencent.could.cos.accessKey");
    private static final String secretKey = PropertiesUtil.getProperty("tencent.could.cos.secretKey");
    private static final String bucket = PropertiesUtil.getProperty("tencent.could.cos.bucket");
    private static final String bucketName = PropertiesUtil.getProperty("tencent.could.cos.bucketName");


    /**
     * * 初始化CosClient相关配置， appid、accessKey、secretKey、region * @return
     */
    public static COSClient getCosClient() {
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey); // 不传APPID也可以，APPID和ACCESSKE已经关联过
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        COSClient cosclient = new COSClient(cred, clientConfig);
        return cosclient;
    }
    @Override
    public Map<String, Object> uploadFileToCOS(MultipartFile file,String moduleName) throws ApiException {
        if(file == null){
            throw new ApiException(BusinessCode.FILE_UPLOAD_NULL_8101.getCode());
        }
        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID()+eName;
        // 1 初始化用户身份信息(fileDomain,secretId, secretKey,bucket,bucketName)
        // 3 生成cos客户端
        COSClient cosclient = getCosClient();
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = null;
        try {
            localFile = File.createTempFile("temp",null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String key = "/"+moduleName+"/"+newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            Map<String,Object> result = new HashMap<String,Object>();
            //logger.info("putObjectResult:{}",putObjectResult.toString());
            logger.info("fileUrl",putObjectRequest.getKey());
            result.put("fileUrl",fileDomain + putObjectRequest.getKey());
            return result;
        } catch (Exception e) {
            throw new ApiException(BusinessCode.FILE_UPLOAD_TO_COS_FAIL_8102.getCode());
        }finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }
    }
}
