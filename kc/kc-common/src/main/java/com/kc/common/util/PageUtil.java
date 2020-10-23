package com.kc.common.util;

import com.kc.common.consts.CommConst;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {

    public static void setPage(Map<String,Object> params){
        if(null == params.get("page")){
            Map<String,Object> pageInfo = new HashMap<String,Object>();
            params.put("startRow",0);
            params.put("pageSize",CommConst.DEFAULT_PAGE_SIZE);
        }else{
            Map<String,Object> pageMap = (Map<String,Object>)params.get("page");
            int curPage = null == pageMap.get("pageNum")? CommConst.DEFAULT_START_PAGE_NUM:Integer.valueOf(String.valueOf(pageMap.get("pageNum")));
            int pageSize = null == pageMap.get("pageSize")?CommConst.DEFAULT_PAGE_SIZE:Integer.valueOf(String.valueOf(pageMap.get("pageSize")));
            int startRow = (curPage - 1) * pageSize;
            params.put("startRow",startRow);
            params.put("pageSize",pageSize);
        }

    }
}
