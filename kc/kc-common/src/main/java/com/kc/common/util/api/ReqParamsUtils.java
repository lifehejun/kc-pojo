package com.kc.common.util.api;
import com.kc.common.consts.CommConst;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by timi on 2018/04/15.
 */
public class ReqParamsUtils {


    /**
     * 获取requst参数列表
     * @param request
     * @return
     */
    public static Map<String,Object> getParamsData(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        //如果有传入分页参数,依照layui分页参数设置
        if(null != map.get("page") && null != map.get("limit")){
            int curPage = null == map.get("page")? CommConst.DEFAULT_START_PAGE_NUM:Integer.valueOf(String.valueOf(map.get("page")));
            int pageSize = null == map.get("limit")?CommConst.DEFAULT_PAGE_SIZE:Integer.valueOf(String.valueOf(map.get("limit")));
            int startRow = (curPage - 1) * pageSize;
            map.put("startRow",startRow);
            map.put("pageSize",pageSize);
        }
      return  map;
    }

    /**
     * 获取requst参数列表
     * @param map
     * @return
     */
    public static Map<String,Object> getApiPageParams(Map<String, Object> map) {
        //如果有传入分页参数,依照layui分页参数设置
        if(null != map.get("page") && null != map.get("limit")){
            int curPage = null == map.get("page")? CommConst.DEFAULT_START_PAGE_NUM:Integer.valueOf(String.valueOf(map.get("page")));
            int pageSize = null == map.get("limit")?CommConst.DEFAULT_PAGE_SIZE:Integer.valueOf(String.valueOf(map.get("limit")));
            int startRow = (curPage - 1) * pageSize;
            map.put("startRow",startRow);
            map.put("pageSize",pageSize);
        }else{
            map.put("startRow",CommConst.DEFAULT_START_PAGE_NUM);
            map.put("pageSize",CommConst.DEFAULT_PAGE_SIZE);
        }
        return  map;
    }

}
