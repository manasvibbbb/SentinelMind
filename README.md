# 🏥 SentinelMind – Healthcare Workflow Intelligence

SentinelMind is a **Java Spring Boot healthcare operations intelligence system** that detects workflow anomalies using learned baselines and explainable rule-based reasoning.

The platform helps hospital administrators identify operational risks such as **understaffing, patient surges, and delayed response times** before they impact patient care.

---

## 🚀 Features

- Workflow data ingestion via REST API
- Baseline learning using historical averages
- Explainable anomaly detection (no black-box ML)
- Persistent storage using SQLite
- Web-based operations dashboard
- Color-coded risk visualization (LOW / MEDIUM / HIGH)

---

## 🧠 How SentinelMind Works

1. **Ingestion**
   - Operational data (patients, staff, response time) is ingested via REST API.
2. **Baseline Learning**
   - The system learns “normal” behavior for each department using rolling averages.
3. **Anomaly Detection**
   - New data is compared against learned baselines using rule-based reasoning.
4. **Explainable Alerts**
   - When anomalies occur, human-readable alerts are generated and stored.
5. **Dashboard Visualization**
   - Alerts are displayed on a web dashboard for quick decision-making.

---

## 🏗 System Architecture

Browser Dashboard
↓
Spring MVC Controllers
↓
Service Layer (Baseline + Anomaly Logic)
↓
Spring Data JPA
↓
SQLite Database

---

## 🧰 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- SQLite
- Thymeleaf
- Maven

---

## ▶️ Run Locally

### Prerequisites

- Java 17+
- Maven 3.9+ (or use the included wrapper)

### Start

```bash
./mvnw spring-boot:run
```

Application URL: `http://localhost:8080`

---

## 🚢 Deploy with Docker

This repository includes a production-friendly multi-stage `Dockerfile`.

### 1) Build image

```bash
docker build -t sentinelmind:latest .
```

### 2) Run container

```bash
docker run -d \
  --name sentinelmind \
  -p 8080:8080 \
  -v sentinelmind-data:/app/data \
  -e SPRING_DATASOURCE_URL=jdbc:sqlite:/app/data/sentinelmind.db \
  sentinelmind:latest
```

### 3) Open app

- `http://localhost:8080/dashboard`
- `http://localhost:8080/api/alerts`

> The named volume keeps SQLite data persistent across restarts.

---

## ☁️ Deploy to a VM (quick recipe)

1. Install Docker on your VM.
2. Copy repo to VM and build image.
3. Run the same `docker run` command above.
4. Open inbound TCP port `8080` in your firewall / security group.
5. (Optional) Put Nginx in front for TLS using Let's Encrypt.

---

## 📡 API Endpoints

### Ingest Workflow Data

```bash
curl -X POST "http://localhost:8080/api/workflow/ingest?department=ER&patients=60&response=20&staff=3"
```

### Alerts

- `GET /api/alerts`
- `GET /dashboard`
