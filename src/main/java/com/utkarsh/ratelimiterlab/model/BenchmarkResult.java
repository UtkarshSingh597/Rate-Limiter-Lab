package com.utkarsh.ratelimiterlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BenchmarkResult {
    private int requests;
    private long manualTimeNs;
    private long bucket4jTimeNs;
    private long manualTimeAvgNs;
    private long bucket4jTimeAvgNs;
}
