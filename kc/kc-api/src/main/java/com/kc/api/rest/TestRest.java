package com.kc.api.rest;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IPostService;
import com.kc.common.base.BaseRest;
import com.kc.common.consts.BankConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(TestRest.class);

    @Autowired
    private IPostService postService;
    @Autowired
    private IBusConfigService busConfigService;

    /**
     * 发布贴子
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            /*Map<String,String> bankMap = BankConst.OUT_MONEY_BANK_CODE_INFO;
            for (String bank: bankMap.keySet()){
                BusConfig busConfig = new BusConfig();
                busConfig.setBusType("chooseBankCard");
                busConfig.setName(bank);
                busConfig.setVal(bankMap.get(bank));
                busConfig.setCreateTime(1596376961L);
                busConfigService.insert(busConfig);
            }*/
            //busConfigService.refresh();
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,add()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * redis刷新
     * @param request
     * @return
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> refresh(HttpServletRequest request) {
        try {

            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,refresh()",500);
            return exceptionHandling(e);
        }
    }


}
