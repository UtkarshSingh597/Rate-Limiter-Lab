package com.utkarsh.ratelimiterlab.controller;


import com.utkarsh.ratelimiterlab.limiter.bucket4j.Bucket4jLimiter;
import com.utkarsh.ratelimiterlab.limiter.manual.TokenBucketLimiter;
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
    public RateLimiterController(TokenBucketLimiter tokenBucketLimiter, Bucket4jLimiter bucket4jLimiter) {
        this.tokenBucketLimiter = tokenBucketLimiter;
        this.bucket4jLimiter = bucket4jLimiter;
    }

    @GetMapping("/manual")
    public RateLimitResult test(@RequestParam String clientId){
        return tokenBucketLimiter.allow(clientId);
    }

    @GetMapping("/bucket4j")
    public RateLimitResult testBucket4j(@RequestParam String clientId){
        return bucket4jLimiter.allow(clientId);
    }
}
