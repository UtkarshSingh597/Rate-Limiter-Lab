# Project Status - Rate Limiter Lab

Last reviewed: 2026-08-31

## Completed

- Spring Boot application and Maven configuration targeting Java 21.
- Shared `RateLimiter` interface and `RateLimitResult` response model.
- Manual token-bucket limiter backed by `InMemoryBucketStore`.
  - `GET /api/rate-limit/manual?clientId={clientId}`
  - Per-client state is held in a `ConcurrentHashMap`.
- Bucket4j limiter with its own in-memory `ConcurrentHashMap`.
  - `GET /api/rate-limit/bucket4j?clientId={clientId}`
- Redis distributed limiter.
  - `RedisBucketStore` saves `TokenBucket` state through `RedisTemplate`.
  - `RedisTokenBucketLimiter` uses `redisBucketStore` and a per-client Redisson lock.
  - `GET /api/rate-limit/redis?clientId={clientId}`
  - `TokenBucket` is serializable for Redis storage.
- Explicit store wiring prevents ambiguity between `InMemoryBucketStore` and `RedisBucketStore`:
  - manual limiter -> `inMemoryBucketStore`;
  - Redis limiter -> `redisBucketStore`.
- Redis implementation verified manually with a live Redis instance:
  - the first 10 immediate requests were allowed;
  - the 11th request was rejected;
  - the bucket key was confirmed in Redis and removed after the test.
- Benchmark endpoint for manual and Bucket4j limiters:
  - `GET /api/benchmark?requests=1000`

## Current state

- The application was last verified to start successfully with Redis on `localhost:6379` and serve requests on port `8080`.
- The application is currently stopped. Redis may be run independently on port `6379`.
- Manual and Bucket4j bucket state is local to one application process and is cleared on restart.
- Redis bucket state can be shared between application instances that use the same Redis server.

## Remaining work

- Add automated unit and integration tests for refill behavior, capacity limits, concurrency, endpoint responses, and Redis persistence.
- Make the manual limiter's per-client read-modify-save update atomic.
- Add expiry/cleanup for inactive Redis bucket keys and in-memory client entries.
- Validate `/api/benchmark` request counts and prevent division by zero.
- Decide whether to include Redis measurements in the benchmark contract; this will need separate reporting of Redis/network overhead.
- Correct or replace the existing context-load test if it is still using the old package name.
