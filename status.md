# Project Status — Rate Limiter Lab

Last reviewed: 2026-08-30

## Completed

- Spring Boot application skeleton and Maven build configuration (Java 21).
- Shared `RateLimiter` interface and common `RateLimitResult` response model.
- Manual, in-memory token-bucket limiter:
  - Per-client buckets are stored in a `ConcurrentHashMap`.
  - Capacity is 10 tokens; one token is refilled per second.
  - Exposes `GET /api/rate-limit/manual?clientId={clientId}`.
- Bucket4j token-bucket limiter:
  - Per-client Bucket4j buckets are stored in a `ConcurrentHashMap`.
  - Uses the same capacity and refill settings as the manual limiter.
  - Exposes `GET /api/rate-limit/bucket4j?clientId={clientId}`.
- Benchmark endpoint for the manual and Bucket4j limiters:
  - `GET /api/benchmark?requests=1000`
  - Includes warm-up calls, total elapsed time, and average time per request.
- Basic Spring Boot context-load test exists (it currently needs package/configuration correction before it passes).

## What is currently happening

- Requests are rate-limited independently for each `clientId`.
- Both working limiters begin a new client with 10 available tokens, consume one token per permitted request, and refill at one token per second.
- All state is local to the application process. Restarting the application clears every bucket.
- The benchmark currently measures method-call time only. It shares persistent benchmark client IDs, so runs after the first may benchmark mostly rejected requests until tokens refill.

## Still to do

- Implement the Redis limiter and wire it into Spring:
  - Add a Redis client dependency and connection properties.
  - Implement `RedisBucketStore` and `RedisTokenBucketLimiter`.
  - Add `GET /api/rate-limit/redis?clientId={clientId}`.
  - Include Redis in the benchmark result and endpoint.
- Make the manual limiter’s read-modify-save update atomic per client. `ConcurrentHashMap` protects the map itself, but simultaneous requests for the same bucket can still race.
- Validate `requests` on `/api/benchmark` (for example, require a positive bounded value); `0` currently causes division by zero.
- Improve benchmark reliability: use fresh/resettable buckets, record allowed/rejected counts, and document JVM warm-up and Redis/network effects.
- Add focused unit and integration tests for refill behavior, capacity limits, concurrent access, endpoint responses, and Redis behavior.
- Fix the existing Spring context test: it is in `com.utkarsh.RateLimterLab`, while the application class is in `com.utkarsh.ratelimiterlab`, so Spring Boot cannot locate the application configuration automatically.
- Define bucket cleanup/expiry so inactive client IDs do not grow in memory indefinitely.
- Update the README to match the current implementation: Redis endpoint and three-way benchmark are described there but are not implemented yet.

## Suggested next milestone

Finish the Redis-backed limiter and its tests, then make all three implementations available through the same API and benchmark contract.
