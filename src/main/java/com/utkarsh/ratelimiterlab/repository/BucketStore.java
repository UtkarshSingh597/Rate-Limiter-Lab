package com.utkarsh.ratelimiterlab.repository;

import com.utkarsh.ratelimiterlab.model.TokenBucket;

public interface BucketStore {
    TokenBucket getBucket(String clientId);
    void save(String clientId, TokenBucket bucket);
}
