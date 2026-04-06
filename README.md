# Auth Service

A production-grade authentication REST API built with Spring Boot, TDD, and deployed with a full CI/CD pipeline.

## Live URL
`https://authservice-vvyb.onrender.com`

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login and receive JWT + refresh token |
| POST | /api/auth/refresh | Get a new access token |
| POST | /api/auth/logout | Invalidate refresh token |

## Tech Stack
- Java 17 + Spring Boot 3.4.1
- Spring Security + JWT (jjwt)
- PostgreSQL + Flyway migrations
- Docker
- GitHub Actions CI/CD
- Neon (managed PostgreSQL)
- Render (cloud hosting)

## Architecture
- Stateless JWT authentication with short-lived access tokens (24h)
- Refresh tokens stored in database for secure invalidation (7 days)
- BCrypt password hashing
- Custom JWT filter on every request

## Testing
- 12 tests across unit, slice and integration layers
- TDD — every feature tested before implementation
- H2 in-memory database for test isolation

## CI/CD
Every push to main triggers the pipeline:
1. GitHub Actions runs full test suite against PostgreSQL
2. Tests passing gates the deployment
3. Render deployment triggered automatically
4. Broken code never reaches production