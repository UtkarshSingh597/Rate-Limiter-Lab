package com.utkarsh.ratelimiterlab.repository;

import com.utkarsh.ratelimiterlab.model.TokenBucket;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class InMemoryBucketStore implements BucketStore {
    private final ConcurrentHashMap<String, TokenBucket > buckets = new ConcurrentHashMap<>();

    @Override
    public TokenBucket getBucket(String clientId){
        return buckets.get(clientId);
    }

    @Override
    public void save(String clientId, TokenBucket bucket) {
        buckets.put(clientId, bucket);
    }
}


