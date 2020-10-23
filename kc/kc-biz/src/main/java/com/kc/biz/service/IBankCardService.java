package com.kc.biz.service;

import com.kc.biz.bean.BankCard;
import com.kc.biz.bean.UserBean;
import com.kc.biz.vo.FollowUserVo;
import com.kc.biz.vo.StatisticsVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IBankCardService {
    int insert(BankCard bankCard);
    int updateById(BankCard bankCard);
    int deleteById(Long id);
    BankCard queryById(Long id);
    BankCard queryByUserId(String userId) throws ApiException;


}
