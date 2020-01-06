package com.policeschool.mall.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: redis
 * 整合redis中出现的问题1：https://blog.csdn.net/hemin1003/article/details/89279232
 * 整合redis中出现的问题2：https://blog.csdn.net/weixin_43538859/article/details/85772742
 * date: Created in 2020/1/6.
 *
 * @author lujingxiao
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    public void set(String key, String value) {
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    public Object get(String key) {
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }
}
