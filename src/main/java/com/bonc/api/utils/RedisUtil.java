package com.bonc.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component
public class RedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
    @Autowired
    private JedisCluster cluster;

    @Value("${spring.redis.timeout}")
    private int timeout;

    public void setString(String key, String value, int expire) {
        cluster.setex(key, expire, value);
    }

    public boolean setNx(String key, String value) {
        return cluster.setnx(key, value) > 0;
    }

    public void setDefaultTimeoutString(String key, String value) {
        cluster.setex(key, timeout, value);
    }

    public void existsIgnoreSetString(String key, String value) {
        if (!cluster.exists(key))
            setString(key, value, timeout);
    }

    public boolean exists(String key) {
        return cluster.exists(key);
    }

    public void delete(String... keys) {
        if (null != keys && keys.length > 0)
            cluster.del(keys);
    }

    public void setExpire(String key,int seconds){
        cluster.expire(key,seconds);
    }

    public String setEx(String key, String value,int timeoutSecond){//yhc on 2019-11-13
        return cluster.setex(key, timeoutSecond,value);
    }

    public String getString(String key) {
        return cluster.get(key);
    }

    public boolean loopTimeout(String key, String value,int timeout) {
        if (setNx(key, value)) {//原来没有该键
            setExpire(key, timeout);
            return true;
        } else {//原来已经有该键
            if (redisKeyTimeout(key,timeout)) {//////原来有该键，且该键未过时时FALSE，原来有该键该键未过时，或原来无该键时TRUE
                return loopTimeout(key, value,timeout);
            }
        }
        return false;//原来有该键，且该键未过时时FALSE
    }

    public boolean smartLoopTimeout(String key, String value) {
        return loopTimeout(key,System.currentTimeMillis()+"_"+value,3600);
    }

    public boolean smartLoopTimeout(String key, String value,int timeout) {
        return loopTimeout(key,System.currentTimeMillis()+"_"+value,timeout);
    }

    public boolean redisKeyTimeout(String tk,int timeout) {
        String string = getString(tk);
        if (!StringUtils.isEmpty(string) && string.contains("_")) {
            String[] value = string.split("_");
            string = value[0];
            try {
                long stkTime = Long.parseLong(string);
                if (stkTime > 1000 && System.currentTimeMillis() - stkTime > (timeout*1000)) {//60分钟
                    delete(tk);
                    LOGGER.info("delete timrout key:{}", tk);
                    return true;
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        return false;//原来有该键，且该键未过时
    }
}
