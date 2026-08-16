package com.utkarsh.ratelimiterlab.service;

import com.utkarsh.ratelimiterlab.limiter.bucket4j.Bucket4jLimiter;
import com.utkarsh.ratelimiterlab.limiter.manual.TokenBucketLimiter;
import com.utkarsh.ratelimiterlab.model.BenchmarkResult;
import org.springframework.stereotype.Service;

@Service
public class BenchmarkService {
    private final TokenBucketLimiter manualLimiter;
    private final Bucket4jLimiter bucket4jLimiter;

    public BenchmarkService(TokenBucketLimiter manualLimiter, Bucket4jLimiter bucket4jLimiter){
        this.bucket4jLimiter = bucket4jLimiter;
        this.manualLimiter = manualLimiter;
    }
    public BenchmarkResult benchmark(int requests){

        String manualClient = "benchmark-manual";
        String bucket4jClient = "benchmark-bucket4j";

        for(int i = 0;i<1000;i++){
            manualLimiter.allow(manualClient);
            bucket4jLimiter.allow(bucket4jClient);
        }
        long startManual = System.nanoTime();

        for(int i = 0; i< requests;i++){
            manualLimiter.allow("benchmark-manual");
        }
        long manualTime = System.nanoTime() - startManual;
        long manualTimeAvg = manualTime / requests;

        long startBucket4j = System.nanoTime();

        for(int i = 0; i<requests;i++){
            bucket4jLimiter.allow(bucket4jClient);
        }
        long bucket4jTime = System.nanoTime() - startBucket4j;
        long bucket4jTimeAvg = bucket4jTime / requests;

        return new BenchmarkResult(
                requests,
                manualTime,
                bucket4jTime,
                manualTimeAvg,
                bucket4jTimeAvg
        );
    }

}
