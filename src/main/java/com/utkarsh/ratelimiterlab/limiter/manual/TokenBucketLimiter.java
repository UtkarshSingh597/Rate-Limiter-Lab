package com.utkarsh.ratelimiterlab.limiter.manual;

import com.utkarsh.ratelimiterlab.limiter.RateLimiter;
import com.utkarsh.ratelimiterlab.model.RateLimiterResult;
import com.utkarsh.ratelimiterlab.model.TokenBucket;
import com.utkarsh.ratelimiterlab.repository.BucketStore;
import org.springframework.stereotype.Component;

@Component
public class TokenBucketLimiter implements RateLimiter {
    private BucketStore bucketStore;
public TokenBucketLimiter (BucketStore bucketStore){
    this.bucketStore = bucketStore;
}
@Override
public RateLimiterResult allow(String clientId){
    TokenBucket bucket = bucketStore.getBucket(clientId);
    if (bucket==null){
        bucket = new TokenBucket(
                10,
                10,
                1,
                System.currentTimeMillis()
        );
        bucketStore.save(clientId, bucket);
    }
    long currentTime = System.currentTimeMillis();
    long elapsedTime = currentTime - bucket.getLastRefillTime();
    int tokenToAdd = (int)(elapsedTime/1000);

    if(tokenToAdd>0){
        bucket.setTokens(
                Math.min(bucket.getCapacity(), tokenToAdd + bucket.getTokens())
        );

        bucket.setLastRefillTime(currentTime);
        bucketStore.save(clientId,bucket);
    }

    if(bucket.getTokens()>0){
        bucket.setTokens(bucket.getTokens()-1);
        bucketStore.save(clientId,bucket);
        return new RateLimiterResult(
                true,
                bucket.getTokens(),
                "Request Allowed",
                System.currentTimeMillis()

        );
    }

    return new RateLimiterResult(
            false,
            0,
            "Rate Limit Exceeded",
            System.currentTimeMillis()
    );
}
}
