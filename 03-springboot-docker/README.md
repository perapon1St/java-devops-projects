# 03 — Spring Boot REST API + Docker

Production-ready Spring Boot REST API containerized with Docker and Docker Compose. Ready to deploy anywhere.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Health check |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

---

## Run Locally

```bash
# Clone repo
git clone https://github.com/perapon1St/java-devops-projects.git
cd java-devops-projects/03-springboot-docker

# Start app + database
docker-compose up -d

# Access API
curl http://localhost:8080/api/health
```

---

## Tech Stack
- Java 17 + Spring Boot 3
- PostgreSQL
- Docker + Docker Compose
- Swagger/OpenAPI (http://localhost:8080/swagger-ui.html)
