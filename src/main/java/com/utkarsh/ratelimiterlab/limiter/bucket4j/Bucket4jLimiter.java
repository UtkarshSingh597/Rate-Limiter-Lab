package com.utkarsh.ratelimiterlab.limiter.bucket4j;

import com.utkarsh.ratelimiterlab.limiter.RateLimiter;
import com.utkarsh.ratelimiterlab.model.RateLimitResult;
import org.springframework.stereotype.Component;
import io.github.bucket4j.Bucket;
import java.time.Duration;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Bucket4jLimiter implements RateLimiter {

    private static final int CAPACITY = 10;
    private static final int REFILL_RATE = 1;

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public RateLimitResult allow(String clientId) {
        Bucket bucket = buckets.computeIfAbsent(clientId, id -> createBucket());
                var probe = bucket.tryConsumeAndReturnRemaining(1);
        if(probe.isConsumed()){
            return new RateLimitResult(
                    true,
                    (int)probe.getRemainingTokens(),
                    "Request Allowed",
                    System.currentTimeMillis()
            );
        }
       return new RateLimitResult(
            false,
           0,
           "Rate Limit Exceeded",
               System.currentTimeMillis()
       );
       }

       private Bucket createBucket(){
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(CAPACITY)
                .refillGreedy(REFILL_RATE, Duration.ofSeconds(1)
                )
                )
                .build();

       }
    }
