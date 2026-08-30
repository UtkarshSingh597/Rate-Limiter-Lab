package com.utkarsh.ratelimiterlab.repository;


import com.utkarsh.ratelimiterlab.model.TokenBucket;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisBucketStore implements BucketStore {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisBucketStore(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public TokenBucket getBucket(String clientId) {
        return (TokenBucket) redisTemplate.opsForValue().get(clientId);
    }

@Override
    public void save(String clientId, TokenBucket bucket) {
        redisTemplate.opsForValue().set(clientId, bucket);
    }



}
