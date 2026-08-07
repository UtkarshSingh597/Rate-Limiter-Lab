# Rate Limiter Lab

A controlled comparison of three architecturally different rate limiting implementations — **Manual Token Bucket**, **Bucket4j**, and **Redis-based Distributed Rate Limiter** — exposed through the same REST API so that only the implementation changes while the interface remains identical.

The goal of this project is not simply to build another rate limiter. It is to understand how different rate limiting approaches behave, compare their implementation complexity, performance, scalability, and operational trade-offs, and document where each approach succeeds or fails.

---

## Contents

- What this is
- Architecture
- Repository Layout
- The Three Rate Limiters
- Request Flow
- API Endpoints
- Comparison
- Benchmarking
- Known Limitations
- Future Work
- Setup
- Usage

---

# What this is

Most tutorials teach only one rate limiting algorithm or rely entirely on a library.

This project implements and compares three different approaches while exposing the same REST API.

Every implementation receives:

- the same request
- the same client ID
- the same rate limits

Only the implementation differs.

This makes it possible to directly compare:

- implementation complexity
- latency
- memory usage
- scalability
- distributed capabilities
- ease of maintenance

---

# Architecture

```
Client
  │
  ▼
RateLimiterController
  │
  ▼
RateLimiter Interface
┌────────────┼────────────┐
│            │            │
▼            ▼            ▼
Manual       Bucket4j     Redis
  │            │            │
  ▼            ▼            ▼
ConcurrentMap Bucket4j     Redis
  │            │            │
  ▼            ▼            ▼
RateLimitResult
```

---

# Repository Layout

```
rate-limiter-lab/
├── controller/
│   └── RateLimiterController.java
│
├── limiter/
│   ├── RateLimiter.java
│   ├── manual/
│   │   └── TokenBucketLimiter.java
│   ├── bucket4j/
│   │   └── Bucket4jLimiter.java
│   └── redis/
│       └── RedisTokenBucketLimiter.java
│
├── model/
│   ├── TokenBucket.java
│   └── RateLimitResult.java
│
├── repository/
│   ├── BucketStore.java
│   ├── InMemoryBucketStore.java
│   └── RedisBucketStore.java
│
└── README.md
```

---

# The Three Rate Limiters

## Manual Token Bucket

Implemented completely from scratch.

Uses:

- ConcurrentHashMap
- custom TokenBucket model
- refill algorithm
- token consumption logic

Purpose:

Understand how Token Bucket works internally without relying on external libraries.

---

## Bucket4j

Uses the Bucket4j library.

Purpose:

Compare a production-ready implementation against the custom implementation.

Questions answered:

- Is it faster?
- Is the code simpler?
- How much functionality comes for free?

---

## Redis Distributed Limiter

Stores bucket state inside Redis.

Purpose:

Demonstrate distributed rate limiting across multiple application instances.

Suitable for:

- multiple servers
- load balancers
- API gateways
- cloud deployments

---

# Request Flow

```

HTTP Request

↓

Controller

↓

Selected Rate Limiter

↓

Check Tokens

↓

Allow / Reject

↓

JSON Response

```

---

# API Endpoints

## Manual

```

GET /api/rate-limit/manual?clientId=alice

```

---

## Bucket4j

```

GET /api/rate-limit/bucket4j?clientId=alice

```

---

## Redis

```

GET /api/rate-limit/redis?clientId=alice

```

---

# Sample Response

```json
{
  "allowed": true,
  "remainingTokens": 7,
  "message": "Request Allowed",
  "timestamp": 1754583412345
}
```

---

# Comparison

| Feature | Manual | Bucket4j | Redis |
|----------|---------|----------|-------|
| Custom Algorithm | ✅ | ❌ | ❌ |
| Easy to Understand | ✅ | ⚠️ | ⚠️ |
| Thread Safe | Planned | ✅ | ✅ |
| Distributed | ❌ | ❌ | ✅ |
| Production Ready | ❌ | ✅ | ✅ |
| Multiple Instances | ❌ | ❌ | ✅ |

---

# Benchmarking

The benchmark endpoint executes the same request through all three implementations and records:

- request latency
- allow/reject result
- remaining tokens
- implementation used

Example:

```json
{
  "manual": {
    "allowed": true,
    "timeNs": 25000
  },
  "bucket4j": {
    "allowed": true,
    "timeNs": 12000
  },
  "redis": {
    "allowed": true,
    "timeNs": 41000
  }
}
```

---

# Known Limitations

- Manual implementation is designed for learning rather than production.
- Current implementation stores bucket state in memory.
- Buckets are lost after application restart.
- Thread safety improvements are planned.
- Benchmark numbers depend on local hardware.

---

# Future Work

- Sliding Window implementation
- Fixed Window implementation
- Leaky Bucket implementation
- Redis Cluster support
- Grafana metrics dashboard
- Prometheus integration
- JMeter load testing
- Kubernetes deployment
- API Gateway integration

---

# Setup

```bash
git clone https://github.com/yourusername/rate-limiter-lab

cd rate-limiter-lab

mvn clean install

mvn spring-boot:run
```

---

# Usage

Manual limiter

```
GET http://localhost:8080/api/rate-limit/manual?clientId=alice
```

Bucket4j limiter

```
GET http://localhost:8080/api/rate-limit/bucket4j?clientId=alice
```

Redis limiter

```
GET http://localhost:8080/api/rate-limit/redis?clientId=alice
```

---

# Learning Objectives

This project demonstrates:

- Token Bucket Algorithm
- Rate Limiting Fundamentals
- ConcurrentHashMap
- Thread Safety
- Spring Boot REST APIs
- Strategy Pattern
- Interface-based Design
- Redis Integration
- Benchmarking Multiple Implementations
- Backend System Design

---

# License

MIT
