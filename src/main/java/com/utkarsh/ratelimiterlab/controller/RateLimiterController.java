package com.utkarsh.ratelimiterlab.controller;


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
    public RateLimiterController(TokenBucketLimiter tokenBucketLimiter) {
        this.tokenBucketLimiter = tokenBucketLimiter;
    }

    @GetMapping("/manual")
    public RateLimitResult test(@RequestParam String clientId){
        return tokenBucketLimiter.allow(clientId);
    }
}
