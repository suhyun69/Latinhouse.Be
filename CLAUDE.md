# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./gradlew build

# Run
./gradlew bootRun

# Test (all)
./gradlew test

# Test (single class)
./gradlew test --tests "com.latinhouse.api.{domain}.{ClassName}"

# Clean build
./gradlew clean build
```

## Architecture

Hexagonal architecture (Ports & Adapters). See `architecture.md` for the full reference including code patterns, security config, and the new-domain checklist.

### Package structure

```
com.latinhouse.api/
  config/          ← SecurityConfig, ApiSecurityConfig, OpenApiConfig
  global/
    exception/     ← ErrorCode (enum), CustomException, ErrorResponse, GlobalExceptionHandler
  security/        ← JwtUtil, JwtRequestFilter, CustomUserDetailsService
  {domain}/
    adapter/in/web/               ← Controller, WebRequest, WebResponse
    adapter/out/persistence/      ← PersistenceAdapter, JpaEntity, Mapper, Repository
    application/service/          ← Service (implements UseCases)
    domain/                       ← pure Java model (no Spring/JPA annotations)
    port/in/                      ← UseCase interfaces + AppRequest/AppResponse DTOs
    port/out/                     ← Port interfaces (Create/Read/Update/Delete)
```

### Dependency flow

```
Controller → UseCase (port/in) ← Service → Port (port/out) ← PersistenceAdapter
```

- Domain and ports have zero dependency on Spring or JPA.
- Service never touches JpaEntity directly; PersistenceAdapter owns that mapping via Mapper.
- Controller never returns JpaEntity.

### Key conventions

- Resource identifier field name: `no` (Long, auto-increment PK)
- URL pattern: `/api/v{n}/{domain}` (lowercase)
- Date input: `yyyy-MM-dd` (String in WebRequest) → `LocalDate` in AppRequest via `from()` factory
- Time input: `HH:mm` (String) → `LocalDateTime` in AppRequest
- Enum codes: short uppercase (e.g. `S`/`B`), parsed via `of(String code)` factory
- AppRequest: `@Value @Builder` (immutable); conversion + business validation in `from(WebRequest)`
- Lombok on entities: `@Data @Builder @NoArgsConstructor @AllArgsConstructor`
- Child entity random ID: 8-char alphanumeric via `SecureRandom`, generated in Service

### Auth

- Stateless JWT; access token 10h, refresh token 7d stored in DB
- `application-secret.yml` holds `custom.jwt.secretKey` (32+ chars) — gitignored; commit only `application-secret.yml.default` as template
- Two filter chains: `SecurityConfig` (global, permits Swagger + H2 console) and `ApiSecurityConfig` (`/api/**`, JWT filter)
