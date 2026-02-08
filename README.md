# OnePay
A simple payment processing service built using Spring Boot that demonstrates idempotent APIs, transactional consistency.

This project focuses on backend fundamentals used in real payment systems, not UI or integrations.
## Features

Create payments with idempotency support

Prevent duplicate payments on retries
Fetch all payments or payment by ID

Delete payments (single / all)
## steps to run locally
Clone the repo
- mvn clean compile
- docker compose up -d
- mvn spring-boot:run
