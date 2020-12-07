package com.kc.biz.service.impl;

import com.kc.biz.bean.BankCard;
import com.kc.biz.bean.Follow;
import com.kc.biz.bean.UserBean;
import com.kc.biz.bean.VipGrade;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.cache.UserCache;
import com.kc.biz.mapper.BankCardMapper;
import com.kc.biz.mapper.PostMapper;
import com.kc.biz.mapper.TransRecordMapper;
import com.kc.biz.mapper.UserMapper;
import com.kc.biz.service.*;
import com.kc.biz.vo.FollowUserVo;
import com.kc.biz.vo.RegIpPhoneVo;
import com.kc.biz.vo.SpreadDetailVo;
import com.kc.biz.vo.StatisticsVo;
import com.kc.common.consts.BankConst;
import com.kc.common.consts.BusConfigConst;
import com.kc.common.consts.CommConst;
import com.kc.common.enums.*;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private TransRecordMapper transRecordMapper;
    @Autowired
    private BankCardMapper bankCardMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private ISpreadDetailService spreadDetailService;
    @Autowired
    private IBusConfigService busConfigService;
    @Autowired
    private ICountService countService;
    @Autowired
    private IAsyncService userAsyncService;
    @Autowired
    private IFilterRuleService filterRuleService;
    @Autowired
    private ICheckBusinessService checkBusinessService;




    @Override
    public int insert(UserBean userBean) {
        return 0;
    }

    @Override
    public int updateById(UserBean entity) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public UserBean queryById(Long id) {
        return userMapper.queryById(id);
    }

    @Override
    public UserBean queryByUserId(String userId) throws ApiException {
        return userMapper.queryByUserId(userId);
    }

    @Override
    public UserBean findByPhone(String phone)throws ApiException  {
        return userMapper.queryByPhone(phone);
    }

    @Override
    public List<UserBean> findByList(Map<String, Object> params) throws ApiException {
        return userMapper.findByList(params);
    }


    @Override
    public UserBean userLogin(String phone, String userPwd, HttpServletRequest request) throws ApiException {
        String regIp = WebUtils.getClientIp(request); //网络ip
        UserBean userBean = userMapper.queryByPhone(phone);
        if (userBean == null) {
            throw new ApiException(BusinessCode.USER_RESP_2005.getCode());
        }
        //校验账号是否冻结
        if(UserStatusEnums.USER_STATUS_0.getStatus().equals(userBean.getStatus())){
            throw new ApiException(BusinessCode.USER_RESP_2007.getCode());
        }
        //CHECK:判断是否开启：校验手机登录黑名单
        String phoneLoginFlag = busConfigService.findByName(RedisKeyEnums.WEB_SWITCH.getCode(),BusConfigConst.NAME_CHECK_PHONE_BLACK_LOGIN);
        if(StringUtils.isNotBlank(phoneLoginFlag) && Boolean.valueOf(phoneLoginFlag)){
            filterRuleService.checkLoginBlackByPhone(phone);
        }

        //CHECK:判断是否开启：校验IP登录黑名单
        String ipLoginFlag = busConfigService.findByName(RedisKeyEnums.WEB_SWITCH.getCode(),BusConfigConst.NAME_CHECK_IP_BLACK_LOGIN);
        if(StringUtils.isNotBlank(ipLoginFlag) && Boolean.valueOf(ipLoginFlag)){
            filterRuleService.checkLoginBlackByIP(phone);
        }


        String md5Pwd = Md5Encrypt.md5(userPwd);
        //密码输入错误
        if(!md5Pwd.equals(userBean.getUserPwd())){
            //增加密码输入失败次数，默认5次
            String key = RedisKeyEnums.PASSWORD_ERROR_TIMES.getCode()+phone;
            String errorTimes = redisUtil.getValueByKey(key);
            long currDayUnixTime = DateTools.getCurrDayUnixTime();
            long todayUnixEndTime = DateTools.getUnixTimestampTime(DateTools.getEndTime(new Date()));
            //缓存时间为当前时间到今日最后一秒到时间差值
            long diffTime =todayUnixEndTime - currDayUnixTime;
            if(StringUtils.isNotBlank(errorTimes)){
                Integer times = Integer.valueOf(errorTimes);
                int lastTimes = (CommConst.DEFAULT_INPUT_USER_PASSWORD_ERROR_TIMES-1) - times.intValue();
                if(lastTimes<=0){
                    //冻结账户
                    userAsyncService.frozenUser(userBean.getId());
                    redisUtil.removeValueByKey(key);
                    throw new ApiException(BusinessCode.USER_RESP_2030.getCode());
                }else{
                    times = times+1;
                    redisUtil.setKeyAndValueTimeout(key,String.valueOf(times),diffTime);
                    throw new ApiException(BusinessCode.USER_RESP_2029.getCode(),new String[]{String.valueOf(times)});
                }

            }else{
                redisUtil.setKeyAndValueTimeout(key,String.valueOf(1),diffTime);
                throw new ApiException(BusinessCode.USER_RESP_2006.getCode());
            }
        }
        return userBean;
    }

    @Override
    public Map<String, Object> userReg(Map<String, String> params, HttpServletRequest request) throws ApiException {
        Map<String,Object> result = new HashMap<String,Object>();
        String regIp = WebUtils.getClientIp(request); //网络ip
        String phone = params.get("phone"); //手机号作为用户名
        String userPwd = params.get("userPwd");
        String verifyCode = params.get("verifyCode");
        String agentCode = params.get("agentCode");
        if(StringUtil.isBlank(phone)){
            throw new ApiException(BusinessCode.USER_RESP_2009.getCode());
        }

        //CHECK:判断是否开启：校验IP注册黑名单
        String ipRegFlag = busConfigService.findByName(RedisKeyEnums.WEB_SWITCH.getCode(),BusConfigConst.NAME_CHECK_IP_BLACK_REG);
        if(StringUtils.isNotBlank(ipRegFlag) && Boolean.valueOf(ipRegFlag)){
            filterRuleService.checkRegBlackByIP(regIp);
        }

        //CHECK:判断是否校验同ip注册个数的限制
        String ipRegSize = busConfigService.findByName(RedisKeyEnums.WEB_SWITCH.getCode(),BusConfigConst.NAME_CHECK_REG_SIZE);
        if(StringUtils.isNotBlank(ipRegSize) && Boolean.valueOf(ipRegSize)){
            checkBusinessService.checkIpRegSize(regIp);
        }

        /** 增加用户注册锁，防止重复提交 **/
        if(redisUtil.lockForRegister(phone)){
            try{
                /** 验证用户名 ：默认为手机号**/
                ValidatorUtil.validatePhone(phone);
                /** 验证登录密码**/
                ValidatorUtil.validatePassword(userPwd);
                /** 验证注册码非空**/
                ValidatorUtil.validateVerifyCode(verifyCode);
                /**验证注册码是否失效,业务校验**/
                //cacheService.checkVerifyCode(verifyCode,phone);
                /** 用户名是否重复注册**/
                UserBean userTemp = userMapper.queryByPhone(phone);
                if(null != userTemp){
                    throw new ApiException(BusinessCode.USER_RESP_2023.getCode());
                }

                UserBean userBean = new UserBean();
                userBean.setUserId(GenerationUtil.generationUserId());
                userBean.setPhone(phone);
                userBean.setUserName(NickNameUtil.getRandomNickname(1));
                userBean.setUserType(UserTypeEnums.USER_TYPE_NORMAL_1.getCode()); //普通用户
                userBean.setUserPwd(Md5Encrypt.md5(userPwd));
                userBean.setBonusRatio(BigDecimal.ZERO);
                userBean.setGrade(CommConst.USER_GRADE_0);
                boolean agentCodeFlag = false; //是否是邀请的用户
                if(StringUtil.isNotBlank(agentCode)){
                    UserBean userBean1 = userMapper.queryByAgentCode(agentCode);
                    if(null != userBean1){
                        userBean.setParentUserId(userBean1.getUserId());
                        agentCodeFlag = true;
                    }else{
                        throw new ApiException(BusinessCode.USER_RESP_2022.getCode());
                    }

                }
                userBean.setStatus(UserStatusEnums.USER_STATUS_1.getStatus()); //正常用户
                userBean.setGrade(CommConst.USER_GRADE_0); //默认普通会员
                userBean.setAgentCode(GenerationUtil.getAgentCode());
                userBean.setRegIp(regIp);
                userBean.setHeadUrl(busConfigService.findByName(RedisKeyEnums.SYS_HEAD_URL.getCode(),String.valueOf(GenerationUtil.getDig(1))));//随机分配一个系统头像
                userMapper.insert(userBean);

                UserBean userLoginInfo = userLogin(phone,userPwd,request);
                result.put("token",tokenService.getToken(userLoginInfo));
                result.put("user", UserCache.refreshLoginUserInfo(userLoginInfo));

                //删除用户注册锁
                redisUtil.removeLockForRegister(phone);

                //推送邀请相关消息
                if(agentCodeFlag){ //是邀请用户才推送邀请信息
                    SpreadDetailVo vo = new SpreadDetailVo();
                    vo.setUserId(userLoginInfo.getUserId());
                    vo.setParentUserId(userLoginInfo.getParentUserId());
                    vo.setBonus(BigDecimal.ZERO);
                    vo.setActiveStatus(CommConst.USER_ACTIVE_STATUS_0);
                    spreadDetailService.syncSpreadDetail(vo);
                }

                //推送统计信息
                countService.syncRegCount(CountTypeEnum.REG.getCode());

                return result;
            }catch (Exception e){
                /** 删除用户注册锁 **/
                redisUtil.removeLockForRegister(phone);
                if (e instanceof  ApiException) {
                    throw (ApiException) e;
                } else {
                    throw new ApiException(BusinessCode.USER_RESP_2016.getCode());
                }
            }

        }else{
            //提示请勿重复提交
            throw new ApiException(BusinessCode.CONSUMER000001.getCode());
        }
    }


    @Override
    public Page<UserBean> queryByPage(Map<String,Object> params) throws ApiException {
        int total = userMapper.getTotal(params);
        if (total > 0) {
            List<UserBean> list = userMapper.queryByPage(params);
            list.forEach(user -> {
                user.setVodGradeName(GradeEnums.getName(user.getGrade()));
                user.setUserTypeDesc(UserTypeEnums.getName(user.getUserType()));
            });
            return new Page<UserBean>(list, total);
        }else{
            return new Page<UserBean>(null, 0);
        }
    }

    @Override
    public Page<RegIpPhoneVo> findPhoneGroupByIp(Map<String,Object> params) throws ApiException {
        int total = userMapper.findPhoneGroupByIpTotal(params);
            if (total > 0) {
            List<RegIpPhoneVo> list = userMapper.findPhoneGroupByIp(params);
            return new Page<RegIpPhoneVo>(list, total);
        }else{
            return new Page<RegIpPhoneVo>(null, 0);
        }
    }


    @Override
    public void submitManualUserReg(String phone,Integer userType) throws ApiException {
        /** 验证用户名 ：默认为手机号**/
        ValidatorUtil.validatePhone(phone);
        UserBean user = userMapper.queryByPhone(phone);
        if(null != user){
            throw new ApiException(BusinessCode.USER_RESP_2023.getCode());
        }
        UserBean userBean = new UserBean();
        userBean.setUserId(GenerationUtil.generationUserId());
        userBean.setUserType(userType); //用户类型
        userBean.setPhone(phone);
        userBean.setUserName(NickNameUtil.getRandomNickname(1));
        userBean.setUserPwd(Md5Encrypt.md5(CommConst.DEFAULT_USER_PASSWORD));
        userBean.setBonusRatio(BigDecimal.ZERO);
        userBean.setStatus(UserStatusEnums.USER_STATUS_1.getStatus()); //正常用户
        userBean.setGrade(CommConst.USER_GRADE_0); //默认普通会员
        userBean.setAgentCode(GenerationUtil.getAgentCode());
        userBean.setRegIp("");
        userBean.setHeadUrl(busConfigService.findByName(RedisKeyEnums.SYS_HEAD_URL.getCode(),String.valueOf(GenerationUtil.getDig(1))));//随机分配一个系统头像
        userMapper.insert(userBean);

        //推送用户注册统计信息
        countService.syncRegCount(CountTypeEnum.REG.getCode());
    }

    @Override
    public StatisticsVo findStatisticsInfo() {
        StatisticsVo statisticsVo = new StatisticsVo();
        //今日注册数
        String todayRegKey = RedisKeyEnums.STATISTICS_REG.getCode()+"today";
        String redisTodayUserReg = redisUtil.getValueByKey(todayRegKey);
        if(StringUtils.isNoneBlank(redisTodayUserReg)){
            statisticsVo.setTodayReg(Integer.valueOf(redisTodayUserReg));
        }else{
            Map<String,Object> todayParams = new HashMap<String,Object>();
            long startDate = DateTools.getCurrDayUnixTime(); //今日凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(new Date()));//今日最后一秒
            todayParams.put("startCreateTime",startDate);
            todayParams.put("endCreateTime",endDate);
            int todayUserReg = userMapper.getTotal(todayParams);
            statisticsVo.setTodayReg(todayUserReg);
            redisUtil.setKeyAndValueTimeout(todayRegKey,String.valueOf(todayUserReg),60*30); //缓存30分钟
        }

        //今日充值
        String todayRechargeKey = RedisKeyEnums.STATISTICS_RECHARGE.getCode()+"today";
        String redisTodayRecharge = redisUtil.getValueByKey(todayRechargeKey);
        if(StringUtils.isNoneBlank(redisTodayRecharge)){
            statisticsVo.setTodayRecharge(new BigDecimal(redisTodayRecharge));
        }else{
            Map<String,Object> transParams = new HashMap<String,Object>();
            long startDate = DateTools.getCurrDayUnixTime(); //今日凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(new Date()));//今日最后一秒
            transParams.put("startDate",startDate);
            transParams.put("endDate",endDate);
            transParams.put("status", CommConst.TRANS_STATUS_1);
            transParams.put("transType", TransTypeEnums.TRANS_TYPE_100.getCode());
            BigDecimal transMoney = transRecordMapper.findSumByTransType(transParams);

            statisticsVo.setTodayRecharge(transMoney);
            redisUtil.setKeyAndValueTimeout(todayRechargeKey,String.valueOf(transMoney),60*30); //缓存30分钟
        }



        //今日发帖数
        String todayPostNumKey = RedisKeyEnums.STATISTICS_POST_NUM.getCode()+"today";
        String redisTodayPostNum = redisUtil.getValueByKey(todayPostNumKey);
        if(StringUtils.isNoneBlank(redisTodayPostNum)){
            statisticsVo.setTodayPostNum(Integer.valueOf(redisTodayPostNum));
        }else{
            Map<String,Object> todayParams = new HashMap<String,Object>();
            long startDate = DateTools.getCurrDayUnixTime(); //今日凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(new Date()));//今日最后一秒
            todayParams.put("startCreateTime",startDate);
            todayParams.put("endCreateTime",endDate);
            int todayPostNum = postMapper.getTotal(todayParams);
            statisticsVo.setTodayPostNum(todayPostNum);
            redisUtil.setKeyAndValueTimeout(todayPostNumKey,String.valueOf(todayPostNum),60*30); //缓存30分钟
        }

        return statisticsVo;
    }

    @Override
    public String findTestUserIdRandom() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userType",UserTypeEnums.USER_TYPE_TEST_2.getCode());
        params.put("status",UserStatusEnums.USER_STATUS_1.getStatus());
        List<UserBean> testUserList = userMapper.findByList(params);
        if(null == testUserList || testUserList.size()<=0){
            throw new ApiException(BusinessCode.USER_RESP_2031.getCode());
        }
        //随机取出一个用户
        Random random = new Random();
        int n = random.nextInt(testUserList.size());
        UserBean userBean = testUserList.get(n);
        return userBean.getUserId();
    }

    @Override
    public void syncUserVipMemberInfo(UserBean user, VipGrade vipGrade,String subServiceId) throws ApiException {

        Integer currGrade = user.getGrade();
        Integer afterGrade = currGrade;
        Integer subServiceIdInt = Integer.valueOf(subServiceId);
        if(subServiceIdInt > currGrade){
            afterGrade = subServiceIdInt;
        }
        int day = GradeEnums.getDay(vipGrade.getGrade());
        Long afterVideoVipEndTime = 0L;
        Long currVideoVipEndTime = user.getVideoVipEndTime();

        if(null == currVideoVipEndTime){
            afterVideoVipEndTime = DateTools.getUnixTimestampTime(DateTools.addDay(new Date(),day));
        }else{
            Date currVideoVipEndDate = DateTools.fromUnixTime(Integer.valueOf(String.valueOf(currVideoVipEndTime)));
            afterVideoVipEndTime = DateTools.getUnixTimestampTime(DateTools.addDay(currVideoVipEndDate,day));
        }
        //更新用户等级及会员结束时间
        user.setGrade(afterGrade);
        user.setVideoVipEndTime(afterVideoVipEndTime);
        userMapper.updateByUserId(user);
    }

    @Override
    public UserBean updateNickName(String userId, String newNickName) throws ApiException {
        ValidatorUtil.validateNotEmpty(newNickName,BusinessCode.USER_RESP_2033);
        UserBean userBean = userMapper.queryByUserId(userId);
        userBean.setUserName(newNickName);
        int res = userMapper.updateByUserId(userBean);
        if(res <=0){
            throw new ApiException(BusinessCode.USER_RESP_2034.getCode());
        }
        return userBean;
    }


    @Override
    public int updateLoginPwd(String userId,String newPwd) throws ApiException {
        ValidatorUtil.validatePassword(newPwd);
        UserBean userBean = userMapper.queryByUserId(userId);
        userBean.setUserPwd(Md5Encrypt.md5(newPwd));
        int res = userMapper.updateByUserId(userBean);
        if(res <=0){
            throw new ApiException(BusinessCode.USER_RESP_2017.getCode());
        }
        return res;
    }

    @Override
    public int bindPhone(String userId,String phone) throws ApiException {
        ValidatorUtil.validatePhone(phone);
        UserBean userBean = userMapper.queryByUserId(userId);
        userBean.setPhone(phone);
        int res = userMapper.updateByUserId(userBean);
        if(res <=0){
            throw new ApiException(BusinessCode.USER_RESP_2018.getCode());
        }
        return res;
    }

    @Override
    public int bindBankCard(Map<String,String> params) throws ApiException {
        String receiver = params.get("receiver");
        String userId = params.get("userId");
        String bankCode = params.get("bankCode");
        String cardNo = params.get("cardNo");
        String subBranch = params.get("subBranch");
        //校验卡号
        ValidatorUtil.validateBankCard(cardNo);
        if(StringUtil.isBlank(receiver)){
            throw new ApiException(BusinessCode.USER_RESP_2019.getCode());
        }
        if(StringUtil.isBlank(bankCode)){
            throw new ApiException(BusinessCode.USER_RESP_2020.getCode());
        }
        BankCard bankCard = new BankCard();
        bankCard.setReceiver(receiver);
        bankCard.setUserId(userId);
        bankCard.setBankCode(bankCode);
        bankCard.setBankName(BankConst.OUT_MONEY_BANK_CODE_INFO.get(bankCode));
        bankCard.setCardNo(cardNo);
        bankCard.setSubBranch(subBranch);
        int res = bankCardMapper.insert(bankCard);
        if(res<=0){
            throw new ApiException(BusinessCode.USER_RESP_2021.getCode());
        }
        return res;
    }

    @Override
    public void getVerifyCode(String phone, String codeType) throws ApiException {
        /** 验证用户名 ：默认为手机号**/
        ValidatorUtil.validatePhone(phone);
        if(!CommConst.VERIFY_CODE_TYPE.contains(codeType)){
            throw new ApiException(BusinessCode.USER_RESP_2024.getCode());
        }
        //调用短信平台获取验证码 //TODO-H
        String verifyCode = "123456";
        if(StringUtils.isNoneBlank(verifyCode)){
            cacheService.setVerifyCode(phone,codeType,verifyCode);
        }
    }

    @Override
    public void follow(Map<String, Object> params) {
        String userId = String.valueOf(params.get("userId"));
        String followedUserId = String.valueOf(params.get("followedUserId"));
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFollowedUserId(followedUserId);
        int followCount = userMapper.isFollowed(follow);
        if(followCount >0){
            throw new ApiException(BusinessCode.FOLLOW_YES_6001.getCode());
        }
        //增加关注关联信息
        userMapper.insertFollow(follow);
    }

    @Override
    public void returnFollow(Map<String, Object> params) {
        String userId = String.valueOf(params.get("userId"));
        String followedUserId = String.valueOf(params.get("followedUserId"));
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFollowedUserId(followedUserId);
        userMapper.deleteFollowById(follow);
    }

    @Override
    public Page<FollowUserVo> findFollowUserByPage(Map<String, Object> params) throws ApiException {

        int total = userMapper.getFollowUserTotal(params);
        if (total > 0) {
            PageUtil.setPage(params); //设置page信息
            List<FollowUserVo> list = userMapper.findFollowUserByPage(params);
            list.forEach(followUserVo -> {
                followUserVo.setAddress(StringUtils.isBlank(followUserVo.getAddress())?CommConst.DEFAULT_USER_ADDRESS:followUserVo.getAddress());
                followUserVo.setGradeDesc(GradeEnums.getName(followUserVo.getGrade()));
            });
            return new Page<FollowUserVo>(list, total);
        }else{
            return new Page<FollowUserVo>(null, 0);
        }
    }

}

