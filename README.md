# Starzplay – Payment Methods Service

REST API that serves payment methods and plans for a video platform, backed by MySQL and secured with JWT.

## Prerequisites

- Java 17
- MySQL running on `localhost:3306`

## Configure

Set your DB credentials in `src/main/resources/application.properties` (defaults: `root`/`root`).
The database `starzplay` is created automatically on first run.

## Database setup

Schema and seed data are created automatically on startup from `src/main/resources/schema.sql`
and `data.sql` (`spring.sql.init.mode=always`). No manual SQL is required.

The seed data includes a login user:

| Username | Password   |
|----------|------------|
| `admin`  | `password` |

To add more users, insert into `users` with a BCrypt-hashed password (e.g. https://bcrypt-generator.com/, cost 10)
and link a row in `user_roles` to a role in `roles`.

## Run

```bash
./mvnw spring-boot:run      # mvnw.cmd on Windows
```

## API

All endpoints require a Bearer JWT except `POST /auth/login`.

1. Login to get a token:

```
POST /auth/login
{ "username": "admin", "password": "password" }
```

2. Call the API with `Authorization: Bearer <jwt>`:

| Method | Path | Notes |
|--------|------|-------|
| GET  | `/api/v1.0/configuration/payment-methods` | All methods; optional `?name=`, `?country=`, or `?id=<planId>` |
| POST | `/api/v1.0/configuration/payment-methods` | Body: JSON array of payment methods |
| PUT  | `/api/v1.0/configuration/payment-methods?payment-methods={id}` | Body: a single payment method |
| GET  | `/api/v1.0/configuration/payment-plans/duration` | Plans grouped by duration |

Errors are returned in a standard shape:

```json
{ "description": "missing payment method name", "httpStatusCode": "400", "requestId": "..." }
```
