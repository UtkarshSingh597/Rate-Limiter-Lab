# Rate Limiter Lab

A Spring Boot lab for comparing three token-bucket rate-limiting approaches through a common REST API:

- a manual in-memory implementation;
- an in-memory implementation using Bucket4j; and
- a Redis-backed implementation using Redisson for distributed locking.

Each limiter starts a new client with 10 tokens and refills one token per second. A permitted request consumes one token.

## Implementations

| Endpoint | Implementation | State location |
| --- | --- | --- |
| `GET /api/rate-limit/manual?clientId=alice` | `TokenBucketLimiter` | `InMemoryBucketStore` (`ConcurrentHashMap`) |
| `GET /api/rate-limit/bucket4j?clientId=alice` | `Bucket4jLimiter` | Its own in-memory `ConcurrentHashMap` |
| `GET /api/rate-limit/redis?clientId=alice` | `RedisTokenBucketLimiter` | `RedisBucketStore` in Redis |

`TokenBucketLimiter` explicitly injects `inMemoryBucketStore`. `RedisTokenBucketLimiter` explicitly injects `redisBucketStore`; it also takes a per-client Redisson lock before updating a bucket. This prevents Spring bean ambiguity and keeps Redis state confined to the distributed limiter.

## Redis setup

The Redis limiter requires Redis at `localhost:6379`. The application is configured through:

- `spring.data.redis.host=localhost`
- `spring.data.redis.port=6379`
- `RedissonConfig`, which connects Redisson to `redis://localhost:6379`

For a local Docker-based Redis instance:

```bash
docker run -d --name rate-limiter-redis -p 6379:6379 redis:7-alpine
```

If the container already exists but is stopped:

```bash
docker start rate-limiter-redis
```

## Run

Requires Java 21 and Maven Wrapper support.

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The server listens on port `8080` by default.

## Example request

```bash
curl "http://localhost:8080/api/rate-limit/redis?clientId=alice"
```

Example response:

```json
{
  "allowed": true,
  "remainingTokens": 9,
  "message": "Request Allowed",
  "timestamp": 1754583412345
}
```

After 10 immediate permitted requests for the same client, the next request is rejected until tokens refill.

## Benchmark endpoint

`GET /api/benchmark?requests=1000` currently benchmarks the manual and Bucket4j implementations. Redis is intentionally not yet included in this benchmark result.

## Current limitations

- The manual limiter's read-modify-save operation is not atomic for concurrent requests for the same client.
- The Redis limiter has no expiry policy for inactive client keys.
- `requests` on the benchmark endpoint is not yet validated; a zero value causes division by zero.
- Automated integration coverage for the Redis endpoint is still to be added.
