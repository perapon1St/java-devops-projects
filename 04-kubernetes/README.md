# 04 — Kubernetes Deployment

Production-grade Kubernetes deployment with auto-scaling, rolling updates, and Harbor registry integration.

---

## Architecture

```
Internet → Ingress → Service → Deployment (Pods) → Harbor Registry
                                    ↕
                               HPA (Auto-scale)
```

## Files

| File | Description |
|------|-------------|
| `deployment.yaml` | App deployment + rolling update |
| `service.yaml` | ClusterIP service |
| `ingress.yaml` | Nginx ingress + SSL |
| `hpa.yaml` | Horizontal Pod Autoscaler |
| `harbor-secret.yaml` | Harbor pull secret |

---

## Deploy

```bash
# Create namespace
kubectl create namespace myapp

# Apply harbor secret
kubectl apply -f harbor-secret.yaml

# Deploy app
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl apply -f ingress.yaml
kubectl apply -f hpa.yaml

# Check status
kubectl get pods -n myapp
kubectl get hpa -n myapp
```

---

## Tech Stack
- Kubernetes 1.28+
- Harbor Registry
- Nginx Ingress Controller
- HPA (Horizontal Pod Autoscaler)
