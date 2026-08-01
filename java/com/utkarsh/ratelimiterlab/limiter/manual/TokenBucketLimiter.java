package com.utkarsh.ratelimiterlab.limiter.manual;

import com.utkarsh.ratelimiterlab.limiter.RateLimiter;
import com.utkarsh.ratelimiterlab.model.RateLimitResult;

public class TokenBucketLimiter implements RateLimiter {

    @Override
    public RateLimitResult allow(String clientId) {
        // TODO: Implement the manual token bucket algorithm.
        return null;
    }
}
