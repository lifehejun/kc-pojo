package com.kc.biz.service;


import com.kc.biz.bean.VideoLabel;
import com.kc.biz.bean.VipGrade;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IVipGradeService {

    Page<VipGrade> queryByPage(Map<String, Object> params) throws ApiException;
}
