# 06 — TikTok Shop API Integration (Spring Boot)

A production-ready Spring Boot microservice that integrates with the [TikTok Shop API via RapidAPI](https://rapidapi.com/Lundehund/api/tiktok-shop-api). Supports product search, product details, seller products, and product reviews.

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.9+
- A RapidAPI key for [TikTok Shop API](https://rapidapi.com/Lundehund/api/tiktok-shop-api)

### Run Locally

```bash
# Clone the repo
git clone https://github.com/perapon1St/java-devops-projects.git
cd java-devops-projects/06-tiktok-shop-api

# Set your RapidAPI key
export RAPIDAPI_KEY=your_rapidapi_key_here

# Build and run
mvn spring-boot:run
```

The service will start on `http://localhost:8080`.

---

## 🐳 Docker

### Build & Run with Docker

```bash
# Build the image
docker build -t tiktok-shop-api:latest .

# Run the container
docker run -d \
  -p 8080:8080 \
  -e RAPIDAPI_KEY=your_rapidapi_key_here \
  --name tiktok-shop-api \
  tiktok-shop-api:latest
```

---

## 📡 API Endpoints

### Product Search

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products/search` | Search products by keyword |
| GET | `/api/products/{productId}` | Get product details |
| GET | `/api/products/{productId}/reviews` | Get product reviews |
| GET | `/api/sellers/{userId}/products` | Get seller products |

---

## 🔧 cURL Examples

### Search Products
```bash
curl "http://localhost:8080/api/products/search?keyword=labubu&region=TH&count=10&cursor=0"
```

### Get Product Detail
```bash
curl "http://localhost:8080/api/products/1234567890?region=TH"
```

### Get Product Reviews
```bash
curl "http://localhost:8080/api/products/1234567890/reviews?region=TH&count=10&cursor=0&sortType=1"
```

### Get Seller Products
```bash
curl "http://localhost:8080/api/sellers/seller123/products?region=TH&count=10&cursor=0"
```

---

## ⚙️ Configuration

| Environment Variable | Default | Description |
|----------------------|---------|-------------|
| `RAPIDAPI_KEY` | *(required)* | Your RapidAPI key |
| `TIKTOK_API_BASE_URL` | `https://tiktok-shop-api.p.rapidapi.com` | API base URL |
| `TIKTOK_API_HOST` | `tiktok-shop-api.p.rapidapi.com` | RapidAPI host header |
| `TIKTOK_API_CONNECT_TIMEOUT_MS` | `5000` | Connect timeout in ms |
| `TIKTOK_API_READ_TIMEOUT_SECONDS` | `10` | Read timeout in seconds |

---

## 📦 Project Structure

```
06-tiktok-shop-api/
├── src/
│   ├── main/
│   │   ├── java/com/tiktokshop/
│   │   │   ├── config/
│   │   │   │   └── TiktokShopConfig.java       # WebClient configuration
│   │   │   ├── client/
│   │   │   │   └── TiktokShopApiClient.java    # HTTP client with retry logic
│   │   │   ├── service/
│   │   │   │   └── TiktokShopService.java      # Business logic & data mapping
│   │   │   ├── controller/
│   │   │   │   ├── ProductController.java      # Product search & detail endpoints
│   │   │   │   ├── SellerController.java       # Seller product endpoints
│   │   │   │   └── ReviewController.java       # Product review endpoints
│   │   │   ├── dto/
│   │   │   │   ├── ProductSearchRequest.java
│   │   │   │   ├── ProductSearchResponse.java
│   │   │   │   ├── ProductDetailResponse.java
│   │   │   │   ├── ProductReviewResponse.java
│   │   │   │   └── SellerProductResponse.java
│   │   │   ├── exception/
│   │   │   │   ├── TiktokShopApiException.java
│   │   │   │   ├── ProductNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── TiktokShopApiApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/tiktokshop/
│           ├── service/
│           │   └── TiktokShopServiceTest.java
│           └── controller/
│               └── ProductControllerTest.java
├── pom.xml
├── Dockerfile
├── .dockerignore
└── README.md
```

---

## 🛠️ Tech Stack

- **Java 17** + **Spring Boot 3**
- **Spring WebClient** (Reactor Netty) for non-blocking HTTP
- **Lombok** for boilerplate reduction
- **Jackson** for JSON serialization
- **JUnit 5** + **Mockito** for unit testing
- **Docker** for containerization

---

## 🔄 Pagination

All list endpoints support cursor-based pagination:

- `cursor` — The cursor returned from the previous response (`next_cursor`)
- `count` — Number of items per page (default: 10)
- `has_more` — Boolean indicating if more results are available

Example loop:
```bash
cursor=0
while true; do
  response=$(curl -s "http://localhost:8080/api/products/search?keyword=labubu&region=TH&count=10&cursor=$cursor")
  echo $response | jq '.products[].title'
  has_more=$(echo $response | jq -r '.has_more')
  cursor=$(echo $response | jq -r '.next_cursor')
  [ "$has_more" = "false" ] && break
done
```

---

## 🧪 Running Tests

```bash
mvn test
```

---

## ☸️ Kubernetes Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: tiktok-shop-api
spec:
  replicas: 2
  selector:
    matchLabels:
      app: tiktok-shop-api
  template:
    metadata:
      labels:
        app: tiktok-shop-api
    spec:
      containers:
      - name: tiktok-shop-api
        image: your-registry/tiktok-shop-api:latest
        ports:
        - containerPort: 8080
        env:
        - name: RAPIDAPI_KEY
          valueFrom:
            secretKeyRef:
              name: tiktok-shop-secrets
              key: rapidapi-key
```

```bash
# Create the secret
kubectl create secret generic tiktok-shop-secrets \
  --from-literal=rapidapi-key=your_rapidapi_key_here

# Apply deployment
kubectl apply -f k8s-deployment.yaml
```
