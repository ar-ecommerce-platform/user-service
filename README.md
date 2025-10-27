# 👤 User Service

User management microservice for the eCommerce platform.  
Handles **registration**, **profile management**, and **user persistence**.

---

## 🧭 Overview

- Provides CRUD APIs for user entities.
- Stores data in **PostgreSQL** (prod) or **H2** (dev).
- Integrated with **Eureka** for service discovery.
- Communicates through **API Gateway**.

---

## 🧪 API Endpoints

| Method | Endpoint | Description |
|---------|-----------|-------------|
| `GET` | `/users` | Get all users |
| `GET` | `/users/{id}` | Get user by ID |
| `POST` | `/users` | Create new user |
| `PUT` | `/users/{id}` | Update user |
| `DELETE` | `/users/{id}` | Delete user |

---

## ▶️ Run Locally

```bash
./gradlew bootRun
```

---

## 🧱 Docker

```bash
docker build -t ecommerce/user-service .
docker run -d -p 8082:8082 ecommerce/user-service
```

---

## 🧰 Related Services

| Service | Port | Purpose |
|----------|------|----------|
| Discovery Server | 8761 | Registry |
| Config Server | 8888 | Centralized configuration management |
| API Gateway | 8080 | Routes traffic |
| Auth Service | 8081 | Authentication |
| User Service | 8082 | User data |
