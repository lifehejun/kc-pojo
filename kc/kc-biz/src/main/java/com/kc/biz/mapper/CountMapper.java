package com.kc.biz.mapper;

import com.kc.biz.bean.Count;
import com.kc.biz.bean.Follow;
import com.kc.biz.bean.UserBean;
import com.kc.biz.vo.FollowUserVo;

import java.util.List;
import java.util.Map;


/**
 * Created by mark on 2018/4/21.
 */
public interface CountMapper extends BaseMapper<Count>{
    List<Count> findByList(Map<String, Object> params);
    List<Count> findCurrMonthByType(Map<String,Object> params);

}
