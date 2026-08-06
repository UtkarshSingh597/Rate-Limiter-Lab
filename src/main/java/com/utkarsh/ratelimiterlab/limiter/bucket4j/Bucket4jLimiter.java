package com.utkarsh.ratelimiterlab.limiter.bucket4j;

import com.utkarsh.ratelimiterlab.model.TokenBucket;

public interface Bucket4jLimiter {

    TokenBucket getBucket(String clientId);
    void saveBucket(String clientId, TokenBucket bucket);
}
