package com.utkarsh.ratelimiterlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenBucket {
    private int capacity;
    private int tokens;
    private int refillRate;
    private long lastRefillTime;
}
