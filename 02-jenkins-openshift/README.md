# 02 — Jenkins Pipeline + OpenShift Deployment

Enterprise-grade CI/CD pipeline using Jenkins declarative pipeline with deployment to OpenShift container platform.

---

## Pipeline Flow

```
Push Code → Jenkins Trigger → Build → Test → Push Harbor → Deploy OpenShift
```

## Stages

| Stage | Description |
|-------|-------------|
| **Checkout** | Pull source code from GitLab |
| **Build** | Maven build + Docker image build |
| **Test** | Run unit tests |
| **Push** | Push to Harbor registry |
| **Deploy** | oc rollout to OpenShift project |

---

## Tech Stack
- Jenkins (Declarative Pipeline)
- OpenShift (oc CLI)
- Harbor Registry
- Java / Spring Boot / Maven
