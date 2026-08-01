package com.utkarsh.ratelimiterlab.repository;

import com.utkarsh.ratelimiterlab.model.TokenBucket;

public class InMemoryBucketStore implements BucketStore {

    @Override
    public TokenBucket findByClientId(String clientId) {
        // TODO: Implement in-memory bucket retrieval.
        return null;
    }

    @Override
    public void save(String clientId, TokenBucket tokenBucket) {
        // TODO: Implement in-memory bucket storage.
    }
}
