package com.kc.biz.mapper;
import com.kc.biz.bean.BankCard;

/**
 * Created by mark on 2018/4/21.
 */
public interface BankCardMapper extends BaseMapper<BankCard>{

    BankCard queryByUserId(String userId);

}
