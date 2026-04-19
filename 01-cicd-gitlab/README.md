# 01 — CI/CD Pipeline with GitLab CI + Harbor

Automated CI/CD pipeline using GitLab CI that builds a Java app, runs tests, pushes Docker image to Harbor registry, and deploys to Kubernetes.

---

## Pipeline Flow

```
Push Code → Build → Test → Push to Harbor → Deploy to K8s
```

## Stages

| Stage | Description |
|-------|-------------|
| **build** | Compile Java app, build Docker image |
| **test** | Run unit tests inside container |
| **push** | Push image to Harbor registry with version tag |
| **deploy** | Deploy to Kubernetes using kubectl |

---

## Tech Stack
- GitLab CI
- Docker + Harbor Registry
- Kubernetes
- Java / Spring Boot

---

## Files

| File | Description |
|------|-------------|
| `.gitlab-ci.yml` | Full pipeline definition |
| `Dockerfile` | Multi-stage Docker build |