package com.utkarsh.ratelimiterlab.model;

public class BenchmarkResult {

    private String limiterName;
    private long durationMillis;
    private long totalRequests;
    private long allowedRequests;
    private long rejectedRequests;

    public BenchmarkResult(String limiterName, long durationMillis, long totalRequests,
                           long allowedRequests, long rejectedRequests) {
        this.limiterName = limiterName;
        this.durationMillis = durationMillis;
        this.totalRequests = totalRequests;
        this.allowedRequests = allowedRequests;
        this.rejectedRequests = rejectedRequests;
    }
}
