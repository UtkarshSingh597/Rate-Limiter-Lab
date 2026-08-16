package com.utkarsh.ratelimiterlab.controller;


import com.utkarsh.ratelimiterlab.model.BenchmarkResult;
import com.utkarsh.ratelimiterlab.service.BenchmarkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/benchmark")
public class BenchmarkController {

    private final BenchmarkService service;

    public BenchmarkController(BenchmarkService service) {
        this.service = service;
    }

    @GetMapping
    public BenchmarkResult benchmark(
            @RequestParam(defaultValue = "1000") int requests){
        return service.benchmark(requests);
    }
}
