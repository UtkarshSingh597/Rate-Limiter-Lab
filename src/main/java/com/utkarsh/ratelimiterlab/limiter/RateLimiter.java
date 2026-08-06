package com.utkarsh.ratelimiterlab.limiter;

import com.utkarsh.ratelimiterlab.model.RateLimiterResult;

public interface RateLimiter {
    RateLimiterResult allow(String clientId);
}
