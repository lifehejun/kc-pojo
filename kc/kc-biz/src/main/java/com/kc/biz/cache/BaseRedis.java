package com.kc.biz.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

public  abstract  class BaseRedis {

    protected abstract RedisTemplate<String, String> getTemplate();

    /**
     * key - value
     * @param key
     * @param value
     */
    public void setKeyAndValue(String key , String value) {
        getTemplate().opsForValue().set(key, value);
    }


    /**
     * key - value
     * @param key
     * @param value
     * @param date
     */
    public void setKeyAndValue(String key , String value, Date date){
        getTemplate().opsForValue().set(key, value);
        getTemplate().expireAt(key, date);
    }

    /**
     * @Title: increment
     * @Description: 递增
     * @param key
     * @param delta
     * @return void
     * @throws
     */
    public Long increment(String key, Long delta) {
        return getTemplate().opsForValue().increment(key, delta);
    }

    /**
     * key - value  - timeout(单位：秒)
     * @param key
     * @param value
     */
    public void setKeyAndValueTimeout(String key , String value,long timeout){
        getTemplate().opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * key 不存在时，设置key value 返回true，如果存在直接返回false
     * @param key
     * @param value
     * @return
     */
    public boolean setKeyAndValueIfAbsent(String key, String value){
        return getTemplate().opsForValue().setIfAbsent(key, value);
    }

    /**
     * key 不存在时，设置key value 返回true，如果存在直接返回false
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean setKeyAndValueTimeountIfAbsent(String key, String value,long timeout){
        boolean result = getTemplate().opsForValue().setIfAbsent(key, value);
        getTemplate().expire(key, timeout, TimeUnit.SECONDS);
        return result;
    }

    /**
     * 根据key查询value
     * @param key
     * @return
     */
    public String getValueByKey(String key){
        return getTemplate().opsForValue().get(key);
    }

    /**
     * 根据key删除value
     * @param key
     */
    public void removeValueByKey(String key){
        getTemplate().delete(key);
    }


    /**
     * 推送消息
     * @param key
     * @param value
     */
    public void leftPush(String key,String value){
        getTemplate().opsForList().leftPush(key, value);
    }

    /**
     * 消费消息
     * @param key
     * @return
     */
    public String rightPop(String key){
        String value = getTemplate().opsForList().rightPop(key);
        if(null == value || "".equals(value))
            return null;
        return value;
    }

    /**
     * 根据key进行模糊匹配查询
     * @param key
     * @return
     */
    public Set<String> getKeys(String key){
        return getTemplate().keys(key);
    }

    /**
     * 设置key在指定时间点失效
     * @param key
     * @param date
     * @return
     */
    public Boolean expireAt(String key, Date date){
        return getTemplate().expireAt(key, date);
    }

    /**
     * 设置key在指定时间点失效
     * @param key
     * @param timeout 单位秒
     * @return
     */
    public Boolean expireAt(String key, long timeout) {
        return getTemplate().expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获得key的失效时间
     * @param key
     * @return
     */
    public Long getExpire(String key){
        return getTemplate().getExpire(key);
    }


    /**
     * redis中是否有key
     * @param key
     */
    public Boolean hasKey(String key){
        return getTemplate().hasKey(key);
    }

    /**
     * redis hash列表中是否有key
     * @param key
     */
    public Boolean hasKey(String hashKey, String key){
        return getTemplate().opsForHash().hasKey(hashKey, key);
    }

    /**
     * @Desciption 查询hash表中所有的value集合
     * @param key
     * @return
     */
    public List<Object> getHashValues(String key){
        return getTemplate().opsForHash().values(key);
    }

    /**
     * @Desciption 查询hash表中所有的key集合
     * @param key
     * @return
     */
    public Set<Object> getHashKeys(String key) {
        return getTemplate().opsForHash().keys(key);
    }

    /**
     * @Desciption 查询hash表中的size
     * @param key
     * @return
     */
    public Long getHashSize(String key) {
        return getTemplate().opsForHash().size(key);
    }

    /**
     * @Title: hset
     * @Description: TODO 操作 - 为哈希表中的字段赋值
     * @param key
     * @param hashKey
     * @param haskValue
     * @return void
     * @throws
     */
    public void hset(String key, Object hashKey, Object haskValue) {
        getTemplate().opsForHash().put(key, hashKey, haskValue);
    }

    public void hsetTimeout(String key, Map<?,Object> value,long timeout){
        hset(key,value);
        getTemplate().expire(key, timeout, TimeUnit.SECONDS);
    }


    /**
     * @Title: hset
     * @Description: TODO 操作 - 为哈希表中的字段赋值
     * @param key
     * @return void
     * @throws
     */
    public void hset(String key, Map<?, Object> values) {
        getTemplate().opsForHash().putAll(key, values);
    }

    /**
     * @Title: hdel
     * @Description: TODO redis hash 操作-删除hash中指定的key
     * @param key
     * @param hashKeys
     * @return void
     * @throws
     */
    public void hdel(String key, Object... hashKeys) {
        getTemplate().opsForHash().delete(key, hashKeys);
    }

    /**
     * @Title: hget
     * @Description: TODO redis hash 操作-根据key获取hash中的值
     * @param key
     * @param hashKeys
     * @return
     * @return Object
     * @throws
     */
    public Object hget(String key, Object hashKeys) {
        return getTemplate().opsForHash().get(key, hashKeys);
    }

    /**
     * @Title: hget
     * @Description: TODO redis hash 操作-根据key获取hash中的值
     * @param key
     * @return
     * @return Object
     * @throws
     */
    public Map<Object, Object> hget(String key) {
        return getTemplate().opsForHash().entries(key);
    }


    /**
     * @Title: hgetKeys
     * @Description: TODO redis hash 操作-根据key获取hash中的值
     * @param key
     * @param hashKeys
     * @return
     * @return Object
     * @throws
     */
    public List<Object> hgetKeys(String key, Collection<Object> hashKeys) {
        return getTemplate().opsForHash().multiGet(key, hashKeys);
    }

    /**
     * @Title: addSetValues
     * @Description: 添加set值
     * @param key
     * @param values
     * @return void
     * @throws
     */
    public Long addSetValues(String key,  String... values) {
        return getTemplate().opsForSet().add(key, values);
    }

    /**
     * @Title: membersSet
     * @Description: 获取set集合值
     * @param key
     * @return Set<String>
     * @throws
     */
    public Set<String> membersSet(String key) {
        return getTemplate().opsForSet().members(key);
    }



    /**
     * @Title: zsetAddValues
     * @Description: 添加zset值
     * @param key
     * @param value
     * @param score
     * @return
     * @return Boolean
     * @throws
     */
    public Boolean zsetAddValues(String key,  String value, double score) {
        return getTemplate().opsForZSet().add(key, value, score);
    }

    /**
     * @Title: zsetRemoveRange
     * @Description: 删除zset值
     * @param key
     * @param start
     * @param end
     * @return Long
     * @throws
     */
    public Long zsetRemoveRange(String key,  long start, long end) {
        return getTemplate().opsForZSet().removeRange(key, start, end);
    }

    /**
     * @Title: zsetRange
     * @Description: 获取zset值
     * @param key
     * @param start
     * @param end
     * @return Set<String>
     * @throws
     */
    public Set<String> zsetRange(String key,  long start, long end) {
        return getTemplate().opsForZSet().range(key, start, end);
    }

    /**
     * @Title: zsetRangeByScore
     * @Description: 获取zset值
     * @param key
     * @return Set<String>
     * @throws
     */
    public Set<String> zsetRangeByScore(String key, long min, long max) {
        return getTemplate().opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * @Title: zsetRemove
     * @Description:zset删除值
     * @param key
     * @param values
     * @return Long
     * @throws
     */
    public Long zsetRemove(String key, Object... values) {
        return getTemplate().opsForZSet().remove(key, values);
    }


    /**
     * @Title: zsetRemoveRangeByScore
     * @Description: 根据score删除zset
     * @param key
     * @param min
     * @param max
     * @return Long
     * @throws
     */
    public Long zsetRemoveRangeByScore(String key, double min, double max) {
        return getTemplate().opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * @Title: zsetSize
     * @Description: 获取set大小
     * @param key
     * @return Long
     * @throws
     */
    public Long zsetSize(String key) {
        return getTemplate().opsForZSet().size(key);
    }


    /**
     *
     * @Title: removeSetValue
     * @Description: set删除值
     * @param key
     * @param values
     * @return void
     * @throws
     */
    public Long removeSetValue(String key, Object... values) {
        return getTemplate().opsForSet().remove(key, values);
    }

    /**
     * @Title: isMember
     * @Description: 判断set值是否存在
     * @param key
     * @param value
     * @return Boolean
     * @throws
     */
    public Boolean isMember(String key, Object value) {
        return getTemplate().opsForSet().isMember(key, value);
    }

    /**
     * @Title: addHyperLogLog
     * @Description: hyperLogLog存值
     * @param key
     * @param values
     * @return Long
     * @throws
     */
    public Long addHyperLogLog(String key, String... values) {
        return getTemplate().opsForHyperLogLog().add(key, values);
    }

    /**
     * @Title: getHyperLogLogSize
     * @Description: 获取key值的数量
     * @param keys
     * @return Long
     * @throws
     */
    public Long getHyperLogLogSize(String... keys) {
        return getTemplate().opsForHyperLogLog().size(keys);
    }
}
