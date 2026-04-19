# 05 — Harbor Private Registry Setup

Set up a production-ready Harbor private container registry with HTTPS and project access control.

---

## Setup Steps

```bash
# 1. Download Harbor installer
wget https://github.com/goharbor/harbor/releases/download/v2.10.0/harbor-online-installer-v2.10.0.tgz
tar xzvf harbor-online-installer-v2.10.0.tgz
cd harbor

# 2. Configure harbor.yml
cp harbor.yml.tmpl harbor.yml
# Edit: hostname, https certificate paths, admin password

# 3. Run installer
sudo ./install.sh
```

---

## Push Image to Harbor

```bash
# Login
docker login harbor.example.com -u admin

# Tag and push
docker tag myapp:latest harbor.example.com/myproject/myapp:1.0.0
docker push harbor.example.com/myproject/myapp:1.0.0
```

---

## Kubernetes Pull Secret

```bash
kubectl create secret docker-registry harbor-secret \
  --docker-server=harbor.example.com \
  --docker-username=YOUR_USER \
  --docker-password=YOUR_PASS \
  --namespace=myapp
```

---

## Key Features

| Feature | Description |
|---------|-------------|
| **Project Policies** | Control who can push/pull |
| **Image Scanning** | Auto-scan on push with Trivy |
| **Replication** | Sync images between registries |
| **HTTPS** | SSL certificate configured |

---

## Tech Stack
- Harbor v2.10
- Docker
- Kubernetes (pull secret integration)
- Trivy (vulnerability scanning)