package com.utkarsh.ratelimiterlab.model;

public class RateLimitResult {

    private boolean allowed;
    private long remainingTokens;

    public RateLimitResult(boolean allowed, long remainingTokens) {
        this.allowed = allowed;
        this.remainingTokens = remainingTokens;
    }
}
