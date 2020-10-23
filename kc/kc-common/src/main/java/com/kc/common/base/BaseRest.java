package com.kc.common.base;

import com.kc.common.consts.RespCode;
import com.kc.common.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by AX on 2018/6/15.
 */
public class BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(BaseRest.class);

    protected Map<String, Object> exceptionHandling(Exception ex) {
        if (ex instanceof ApiException) {
            ApiException apiExc = (ApiException) ex;
            logger.error("应用异常！", ex);
            return resultLinkedHashMap(apiExc.getCode(), apiExc.getMessage(), null);
        } else {
            logger.error("系统异常！", ex);
            return requestError("系统异常，请联系管理员!"+ex.getMessage());
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
        return resultLinkedHashMap(RespCode.APP_ERROR, msg, data);
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
        return resultLinkedHashMap(RespCode.OK, "success", data);
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
        return resultLinkedHashMap(RespCode.OK, msg, data);
    }
    /**
     * 请求成功
     * @param msg
     * @return LinkedHashMap
     */
    protected LinkedHashMap<String, Object> requestSuccess(String msg) {
        return resultLinkedHashMap(RespCode.OK, msg,null);
    }

    /**
     * @Title: resultLinkedHashMap
     * @param code 响应吗
     * @param msg 提示信息
     * @param data 数据
     * @return LinkedHashMap
     * @throws
     */
    private LinkedHashMap<String, Object> resultLinkedHashMap(String code, String msg, Object data) {
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", data);
        return resultMap;
    }


}
