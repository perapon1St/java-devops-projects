# 06 — TikTok Shop API — Spring Boot Microservice

A production-ready Spring Boot microservice that integrates with the [TikTok Shop API](https://rapidapi.com/Lundehund/api/tiktok-shop-api) via RapidAPI. Part of the **Java DevOps Portfolio**.

---

## 🚀 Features

- Search products by keyword
- Get product detail by ID
- Fetch product reviews with sorting and pagination
- List products from a specific seller
- Reactive HTTP client (WebClient) with timeout and error handling
- Global exception handling with proper HTTP status codes
- Docker-ready multi-stage build

---

## 📋 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products/search?keyword=labubu&region=TH` | Search products by keyword |
| GET | `/api/products/{id}?region=TH` | Get product detail by ID |
| GET | `/api/products/{id}/reviews?region=TH&sortType=1` | Get product reviews |
| GET | `/api/sellers/{userId}/products?region=TH` | Get seller's products |
| GET | `/actuator/health` | Health check |

### Query Parameters

**Search Products** (`GET /api/products/search`):
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `keyword` | string | required | Search keyword |
| `region` | string | `TH` | Region code (TH, US, etc.) |
| `count` | int | `10` | Number of results |
| `cursor` | int | `0` | Pagination cursor |

**Product Reviews** (`GET /api/products/{id}/reviews`):
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `region` | string | `TH` | Region code |
| `count` | int | `10` | Number of reviews |
| `cursor` | int | `0` | Pagination cursor |
| `sortType` | int | `1` | 1 = Relevance, 2 = Recent |

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.2
- Spring WebFlux (WebClient)
- Lombok
- Jackson
- JUnit 5 + Mockito
- Docker (multi-stage build)

---

## ⚙️ Configuration

The application reads API credentials from environment variables:

| Variable | Description |
|----------|-------------|
| `RAPIDAPI_KEY` | Your RapidAPI key for TikTok Shop API |

Get your API key at [RapidAPI TikTok Shop](https://rapidapi.com/Lundehund/api/tiktok-shop-api).

### application.yml

```yaml
tiktokshop:
  api:
    base-url: https://tiktok-shop-api.p.rapidapi.com
    rapidapi-key: ${RAPIDAPI_KEY:your-key-here}
    rapidapi-host: tiktok-shop-api.p.rapidapi.com
    timeout-seconds: 10
```

---

## 🐳 Run with Docker

```bash
# Build image
docker build -t tiktok-shop-api .

# Run container
docker run -p 8080:8080 \
  -e RAPIDAPI_KEY=your-rapidapi-key \
  tiktok-shop-api
```

---

## 💻 Run Locally

```bash
# Set environment variable
export RAPIDAPI_KEY=your-rapidapi-key

# Build and run
mvn spring-boot:run

# Or build JAR first
mvn clean package -DskipTests
java -jar target/tiktok-shop-api-1.0.0.jar
```

---

## 🧪 Run Tests

```bash
mvn test
```

---

## 📡 Example Requests

### Search Products

```bash
curl "http://localhost:8080/api/products/search?keyword=labubu&region=TH&count=5"
```

**Response:**
```json
{
  "products": [
    {
      "product_id": "1234567890",
      "title": "Labubu Monster Plush Toy",
      "cover_image": "https://...",
      "price": {
        "currency": "THB",
        "original_price": 599.0,
        "sale_price": 499.0,
        "discount_percent": 17
      },
      "sold_count": 1250,
      "rating": 4.8,
      "review_count": 320,
      "seller": {
        "seller_id": "seller123",
        "seller_name": "Pop Mart Official",
        "seller_tiktok_id": "@popmart"
      }
    }
  ],
  "nextCursor": 10,
  "hasMore": true,
  "total": 150
}
```

### Get Product Detail

```bash
curl "http://localhost:8080/api/products/1234567890?region=TH"
```

### Get Product Reviews

```bash
curl "http://localhost:8080/api/products/1234567890/reviews?region=TH&sortType=2&count=5"
```

### Get Seller Products

```bash
curl "http://localhost:8080/api/sellers/seller123/products?region=TH&count=10"
```

---

## 📁 Project Structure

```
06-tiktok-shop-api/
├── src/main/java/com/tiktokshop/
│   ├── TiktokShopApiApplication.java   # Main entry point
│   ├── config/
│   │   └── TiktokShopConfig.java       # WebClient bean configuration
│   ├── client/
│   │   └── TiktokShopApiClient.java    # HTTP client wrapper
│   ├── service/
│   │   └── TiktokShopService.java      # Business logic layer
│   ├── controller/
│   │   ├── ProductController.java      # /api/products endpoints
│   │   ├── SellerController.java       # /api/sellers endpoints
│   │   └── ReviewController.java      # /api/products/{id}/reviews
│   ├── dto/
│   │   ├── ProductSearchRequest.java
│   │   ├── ProductSearchResponse.java
│   │   ├── ProductDetailResponse.java
│   │   ├── ProductReviewResponse.java
│   │   └── SellerProductResponse.java
│   └── exception/
│       ├── TiktokShopApiException.java
│       ├── ProductNotFoundException.java
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   └── application.yml
├── src/test/java/com/tiktokshop/
│   ├── service/TiktokShopServiceTest.java
│   └── controller/ProductControllerTest.java
├── Dockerfile
├── .dockerignore
└── README.md
```

---

## 🔗 References

- [TikTok Shop API on RapidAPI](https://rapidapi.com/Lundehund/api/tiktok-shop-api)
- [Python reference implementation](https://github.com/Lundehund/tiktok-shop-api)
