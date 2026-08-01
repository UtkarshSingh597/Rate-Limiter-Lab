package com.utkarsh.ratelimiterlab.repository;

import com.utkarsh.ratelimiterlab.model.TokenBucket;

public interface BucketStore {

    TokenBucket findByClientId(String clientId);

    void save(String clientId, TokenBucket tokenBucket);
}
