package com.utkarsh.ratelimiterlab.controller;


import com.utkarsh.ratelimiterlab.limiter.RateLimiter;
import com.utkarsh.ratelimiterlab.limiter.bucket4j.Bucket4jLimiter;
import com.utkarsh.ratelimiterlab.limiter.manual.TokenBucketLimiter;
import com.utkarsh.ratelimiterlab.limiter.redis.RedisTokenBucketLimiter;
import com.utkarsh.ratelimiterlab.model.RateLimitResult;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limit")
public class RateLimiterController {
    private final TokenBucketLimiter tokenBucketLimiter;
    private final Bucket4jLimiter bucket4jLimiter;
    private final RedisTokenBucketLimiter redisTokenBucketLimiter;

    public RateLimiterController(TokenBucketLimiter tokenBucketLimiter, Bucket4jLimiter bucket4jLimiter, RedisTokenBucketLimiter redisTokenBucketLimiter) {
        this.tokenBucketLimiter = tokenBucketLimiter;
        this.bucket4jLimiter = bucket4jLimiter;
        this.redisTokenBucketLimiter = redisTokenBucketLimiter;
    }

    @GetMapping("/manual")
    public RateLimitResult test(@RequestParam String clientId){
        return tokenBucketLimiter.allow(clientId);
    }

    @GetMapping("/bucket4j")
    public RateLimitResult testBucket4j(@RequestParam String clientId){
        return bucket4jLimiter.allow(clientId);
    }

    @GetMapping("/redis")
    public RateLimitResult testRedis(@RequestParam String clientId){
        return redisTokenBucketLimiter.allow(clientId);
    }

}
