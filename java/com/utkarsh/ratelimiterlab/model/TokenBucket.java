package com.utkarsh.ratelimiterlab.model;

public class TokenBucket {

    private long capacity;
    private long availableTokens;
    private long refillRate;
    private long lastRefillTimestamp;

    public TokenBucket(long capacity, long availableTokens, long refillRate, long lastRefillTimestamp) {
        this.capacity = capacity;
        this.availableTokens = availableTokens;
        this.refillRate = refillRate;
        this.lastRefillTimestamp = lastRefillTimestamp;
    }
}
