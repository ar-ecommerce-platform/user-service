# user-service

User profile management for the [ar-ecommerce-platform](https://github.com/ar-ecommerce-platform).

- **Port:** 8087
- **Persistence:** in-memory H2 (`user_profiles` table) — resets on restart
- **Registers with:** Eureka (discovery-server :8761)

## Endpoints

Reached through the gateway as `/api/users/**`.

| Method | Path | Body / query | Result |
|---|---|---|---|
| `POST` | `/users` | `{ email, displayName }` | `201` — idempotent: returns the existing profile for a known email |
| `GET` | `/users` | — | list all |
| `GET` | `/users/{id}` | — | one profile, `404` if missing |
| `GET` | `/users/by-email` | `?email=` | one profile, `404` if missing |

**API docs:** Swagger UI at `http://localhost:8087/swagger-ui.html` (OpenAPI JSON at `/v3/api-docs`).

## Run

Whole platform (recommended):

```bash
docker compose -f ../infra/compose/docker-compose.yml up -d --build
```

This service alone:

```bash
./gradlew bootRun
# or
docker build -t ecom/user-service . && docker run --rm -p 8087:8087 ecom/user-service
```

## Build & quality

```bash
./gradlew build          # compile + test + spotless + checkstyle (cyclomatic complexity <= 10) + jacoco report
./gradlew spotlessApply
```

Quality config is vendored: `gradle/quality.gradle`, `config/checkstyle/`.

## Config

| Variable | Default | Purpose |
|---|---|---|
| `SERVER_PORT` | `8087` | HTTP port |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | `http://localhost:8761/eureka/` | registry URL |

## Tech

Java 21 · Spring Boot 3.5.7 · Spring Data JPA + H2 · Bean Validation ·
Spring Cloud 2025.0.0 (`netflix-eureka-client`) · Gradle

See [infra/RUNBOOK.md](../infra/RUNBOOK.md) for the full platform runbook.
