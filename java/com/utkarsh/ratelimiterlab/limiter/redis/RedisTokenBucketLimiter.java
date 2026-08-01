package com.utkarsh.ratelimiterlab.limiter.redis;

import com.utkarsh.ratelimiterlab.limiter.RateLimiter;
import com.utkarsh.ratelimiterlab.model.RateLimitResult;

public class RedisTokenBucketLimiter implements RateLimiter {

    @Override
    public RateLimitResult allow(String clientId) {
        // TODO: Implement Redis token bucket rate limiting.
        return null;
    }
}
