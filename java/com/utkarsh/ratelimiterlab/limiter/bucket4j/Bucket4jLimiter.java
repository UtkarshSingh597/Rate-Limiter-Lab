package com.utkarsh.ratelimiterlab.limiter.bucket4j;

import com.utkarsh.ratelimiterlab.limiter.RateLimiter;
import com.utkarsh.ratelimiterlab.model.RateLimitResult;

public class Bucket4jLimiter implements RateLimiter {

    @Override
    public RateLimitResult allow(String clientId) {
        // TODO: Implement Bucket4j rate limiting.
        return null;
    }
}
