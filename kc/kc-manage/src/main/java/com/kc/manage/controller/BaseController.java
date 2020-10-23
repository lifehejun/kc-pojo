package com.kc.manage.controller;

import com.kc.common.consts.RespCode;
import com.kc.common.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public class BaseController {


    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * @Title: exceptionHandling
     * @Description: TODO 各类异常处理，包括业务异常与系统异常
     * @param e
     * @return LinkedHashMap
     * @throws
     */
    protected LinkedHashMap<String, Object> exceptionHandling(Exception e) {
        if (e instanceof ApiException) { //代理不存在
            ApiException apiExc = (ApiException) e;
            logger.error(apiExc.getMessage());
            return resultLinkedHashMap(apiExc.getCode(), apiExc.getMessage(), null,0);
        } else {
            logger.error("系统异常！", e);
            return requestError("系统异常！" + e.getMessage());
        }

    }

    /**
     * @Title: requestError
     * @Description:  请求失败
     * @param msg
     * @return LinkedHashMap
     * @throws
     */
    protected LinkedHashMap<String, Object> requestError(String msg) {
        return requestError(msg, null);
    }

    /**
     * @Title: requestError
     * @Description:  请求失败
     * @param msg
     * @param data
     * @return LinkedHashMap
     * @throws
     */
    protected LinkedHashMap<String, Object> requestError(String msg, Object data) {
        return resultLinkedHashMap(RespCode.APP_ERROR, msg, data,0);
    }


    /**
     * @Title: requestSuccess
     * @Description:  请求成功
     * @return LinkedHashMap
     * @throws
     */
    protected LinkedHashMap<String, Object> requestSuccess() {
        return requestSuccess("success");
    }

    /**
     * @Title: requestSuccess
     * @Description:  请求成功
     * @param data
     * @return LinkedHashMap
     * @throws
     */
    protected LinkedHashMap<String, Object> requestSuccess(Object data) {
        return resultLinkedHashMap(RespCode.OK, "success", data,0);
    }

    protected LinkedHashMap<String, Object> requestSuccess(Object data,int count) {
        return resultLinkedHashMap(RespCode.OK, "success", data,count);
    }


    /**
     * @Title: requestSuccess
     * @Description:  请求成功
     * @param msg
     * @param data
     * @return LinkedHashMap
     * @throws
     */
    protected LinkedHashMap<String, Object> requestSuccess(String msg,Object data) {
        return resultLinkedHashMap(RespCode.OK, msg, data,0);
    }

    protected LinkedHashMap<String, Object> requestSuccess(String msg,Object data,int count) {
        return resultLinkedHashMap(RespCode.OK, msg, data,count);
    }
    /**
     * 请求成功
     * @param msg
     * @return LinkedHashMap
     */
    protected LinkedHashMap<String, Object> requestSuccess(String msg) {
        return resultLinkedHashMap(RespCode.OK, msg,null,0);
    }

    /**
     * @Title: resultLinkedHashMap
     * @param code 响应吗
     * @param msg 提示信息
     * @param data 数据
     * @return LinkedHashMap
     * @throws
     */
    private LinkedHashMap<String, Object> resultLinkedHashMap(String code, String msg, Object data,int count) {
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("count",count);
        resultMap.put("data", data);
        return resultMap;
    }
}
