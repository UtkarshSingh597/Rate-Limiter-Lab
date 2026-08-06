package com.utkarsh.ratelimiterlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RateLimiterResult {

    private boolean allowed;

    private int remainingTokens;

    private String message;

    private long timestamp;

}
