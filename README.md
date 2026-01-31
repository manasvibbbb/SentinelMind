<<<<<<< HEAD
# 🏥 SentinelMind – Healthcare Workflow Intelligence

SentinelMind is a **Java Spring Boot–based healthcare operations intelligence system** that detects workflow anomalies using learned baselines and explainable rule-based reasoning.

The system helps hospital administrators identify operational risks such as **understaffing, patient surges, and delayed response times** before they impact patient care.

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
Browser (Dashboard)

↓

Spring MVC Controller

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

## 📡 API Endpoints

### Ingest Workflow Data

**Parameters:**
- department
- patients
- response
- staff

**Example:**
```bash
curl -X POST "http://localhost:8080/api/workflow/ingest?department=ER&patients=60&response=20&staff=3"

GET /api/alerts
GET /dashboard
=======
# SentinelMind
>>>>>>> a512681509256b985da2cd0986bf4047b143578f
