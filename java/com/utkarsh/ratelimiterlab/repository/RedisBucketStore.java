package com.utkarsh.ratelimiterlab.repository;

import com.utkarsh.ratelimiterlab.model.TokenBucket;

public class RedisBucketStore implements BucketStore {

    @Override
    public TokenBucket findByClientId(String clientId) {
        // TODO: Implement Redis bucket retrieval.
        return null;
    }

    @Override
    public void save(String clientId, TokenBucket tokenBucket) {
        // TODO: Implement Redis bucket storage.
    }
}
