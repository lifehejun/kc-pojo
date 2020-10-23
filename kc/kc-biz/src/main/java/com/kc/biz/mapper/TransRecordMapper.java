package com.kc.biz.mapper;

import com.kc.biz.bean.TransRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransRecordMapper extends BaseMapper<TransRecord> {

    List<TransRecord> queryByPage(Map<String, Object> params);
    TransRecord queryByTransNo(String transNo);
    BigDecimal findSumByTransType(Map<String, Object> params);

}
