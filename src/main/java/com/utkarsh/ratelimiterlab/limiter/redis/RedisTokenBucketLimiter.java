package com.utkarsh.ratelimiterlab.limiter.redis;

import com.utkarsh.ratelimiterlab.limiter.RateLimiter;
import com.utkarsh.ratelimiterlab.model.RateLimitResult;
import com.utkarsh.ratelimiterlab.model.TokenBucket;
import com.utkarsh.ratelimiterlab.repository.BucketStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@Component
public class RedisTokenBucketLimiter implements RateLimiter {
    private static final int CAPACITY = 10;
    private static final int REFILL_RATE = 1;

    private final BucketStore bucketStore;
    private final RedissonClient redissonClient;

    public RedisTokenBucketLimiter( @Qualifier("redisBucketStore") BucketStore bucketStore,RedissonClient redissonClient){
        this.bucketStore = bucketStore;
        this.redissonClient = redissonClient;
    }
    @Override
    public RateLimitResult allow(String clientId){
        RLock lock = redissonClient.getLock("rate-limit-lock:" + clientId);
        lock.lock();
        try {
            TokenBucket bucket = bucketStore.getBucket(clientId);
            boolean bucketCreated = false;

            if (bucket == null) {
                long currentTime = System.currentTimeMillis();
                bucket = new TokenBucket(CAPACITY, CAPACITY, REFILL_RATE, currentTime);
                bucketCreated = true;
            }
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - bucket.getLastRefillTime();
            int tokensToAdd = (int) (elapsedTime / 1000L);
            boolean bucketRefilled = tokensToAdd > 0;

            if (bucketRefilled) {
                int refilledTokens = bucket.getTokens() + tokensToAdd;
                int availableTokens = Math.min(bucket.getCapacity(), refilledTokens);
                long updatedRefillTime = bucket.getLastRefillTime() + (tokensToAdd * 1000L);

                bucket.setTokens(availableTokens);
                bucket.setLastRefillTime(updatedRefillTime);
            }
            RateLimitResult result;
            if (bucket.getTokens() > 0) {
                int remainingTokens = bucket.getTokens() - 1;
                bucket.setTokens(remainingTokens);
                result = new RateLimitResult(
                        true,
                        remainingTokens,
                        "Request Allowed",
                        currentTime
                );
            } else {


                result = new RateLimitResult(
                        false,
                        0,
                        "Rate Limit Exceeded",
                        currentTime
                );
            }
            bucketStore.save(clientId, bucket);
            return result;
        } finally{
            lock.unlock();
        }
    }
}