package com.utkarsh.ratelimiterlab.limiter;

import com.utkarsh.ratelimiterlab.model.RateLimitResult;

public interface RateLimiter {
    RateLimitResult allow(String clientId);
}
