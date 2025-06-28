# Personal Finance Manager

A comprehensive web-based system for managing personal finances, tracking income, expenses, and savings goals. Built with Java 17, Spring Boot 3, and H2 database.  
**Live Demo:** _[https://syfetask.onrender.com/swagger-ui/index.html]_  
**Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Table of Contents

- [Assignment Overview](#assignment-overview)
- [Features](#features)
- [API Documentation](#api-documentation)
- [Setup & Running Locally](#setup--running-locally)
- [Deployment](#deployment)
- [Testing](#testing)
- [Design Decisions](#design-decisions)
- [Project Structure](#project-structure)
- [Tech Stack](#tech-stack)
- [Contact](#contact)

---

## Assignment Overview

This project is a solution to the **System Design and Implementation Assignment** for a Personal Finance Manager.  
It enables users to:

- Register, login, and manage their sessions securely.
- Track income and expenses with full CRUD operations.
- Categorize transactions (default and custom categories).
- Set and monitor savings goals.
- Generate monthly and yearly financial reports.

---

## Features

### 1. User Management & Authentication

- **Registration:** Email, password, full name, phone number.
- **Login/Logout:** Session-based authentication (secure cookies).
- **Data Isolation:** Users can only access their own data.

### 2. Transaction Management

- **CRUD:** Create, read (with filters), update, and delete transactions.
- **Validation:** Amount, date, and category checks.

### 3. Category Management

- **Default Categories:** Predefined, immutable categories.
- **Custom Categories:** User-defined, unique per user, deletable if unused.

### 4. Savings Goals

- **Goal Creation:** Name, target amount, target date, start date.
- **Progress Tracking:** Based on income and expenses since goal start.
- **Management:** View, update, delete goals.

### 5. Reports & Analytics

- **Monthly/Yearly Reports:** Income, expenses, net savings by category.

---

## API Documentation

- **Interactive Swagger UI:**  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

- **Base URL (local):** `http://localhost:8080/api`
- **Base URL (deployed):** `https://syfetask.onrender.com/api`

### Example Endpoints

- `POST /api/auth/register` – Register a new user
- `POST /api/auth/login` – Login and receive session cookie
- `POST /api/auth/logout` – Logout and invalidate session
- `GET /api/transactions` – List/filter transactions
- `POST /api/transactions` – Create a transaction
- `PUT /api/transactions/{id}` – Update a transaction
- `DELETE /api/transactions/{id}` – Delete a transaction
- `GET /api/categories` – List all categories
- `POST /api/categories` – Create a custom category
- `DELETE /api/categories/{name}` – Delete a custom category
- `POST /api/goals` – Create a savings goal
- `GET /api/goals` – List all goals
- `PUT /api/goals/{id}` – Update a goal
- `DELETE /api/goals/{id}` – Delete a goal
- `GET /api/reports/monthly/{year}/{month}` – Monthly report
- `GET /api/reports/yearly/{year}` – Yearly report

_See Swagger UI for full request/response details and error codes._

---

## Setup & Running Locally

### Prerequisites

- Java 17+
- Maven

### Steps

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/helloGuyzs/syfetask.git]
   cd syfetask
   ```

2. **Build the project:**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The app will start on [http://localhost:8080](http://localhost:8080).

4. **Access Swagger UI:**  
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Deployment

- **Platform:** [Render](https://render.com/)
- **Database:** H2 (in-memory)
- **Live API:** _[https://syfetask.onrender.com/]_

---

## Testing

- **Test Script:**  
  Run the provided test script to verify all APIs:
  ```bash
  bash scripts/financial_manager_tests.sh [BASE_URL]
  ```
  Example:
  ```bash
  bash scripts/financial_manager_tests.sh https://syfetask.onrender.com/api
  ```

- **Expected Output:**  
  ```
  TEST EXECUTION SUMMARY
  ================================
  Total Tests Executed: 86
  Tests Passed: 86
  Tests Failed: 0
  Success Rate: 100%
  🎉 ALL TESTS PASSED! 🎉
  ```


## Screenshots


1. ![Screenshot 1](https://github.com/helloGuyzs/syfetask/blob/main/Screenshot%202025-06-28%20203657.png)
2. ![Screenshot 2](https://github.com/helloGuyzs/syfetask/blob/main/Screenshot%202025-06-28%20203718.png)
3. ![Screenshot 3](https://github.com/helloGuyzs/syfetask/blob/main/Screenshot%202025-06-28%20203737.png)
4. ![Screenshot 4](https://github.com/helloGuyzs/syfetask/blob/main/Screenshot%202025-06-28%20203753.png)

### Test Case Completion

![Test Case Completed](https://github.com/helloGuyzs/syfetask/blob/main/Test_case_completed.png)  


## Design Decisions

- **Layered Architecture:** Controller → Service → Repository.
- **DTOs:** All API requests/responses use DTOs, not entities.
- **Exception Handling:** Centralized with `@ControllerAdvice`.
- **Validation:** All inputs validated; descriptive error messages.
- **Security:** Spring Security with session-based authentication.
- **Configuration:** Externalized in `application.properties`.
- **Documentation:** Swagger/OpenAPI, JavaDoc for public methods/classes.

---

## Project Structure


src/main/java/com/helloguyzs/syfetask/
├── config/ # Security configuration
├── controller/ # REST controllers (API endpoints)
├── dto/ # Data Transfer Objects (requests/responses)
├── enums/ # Enum types (e.g., CategoryType)
├── exceptions/ # Custom exceptions & global handler
├── models/ # JPA entities (User, Transaction, etc.)
├── repo/ # Spring Data repositories
├── services/ # Business logic/services
├── utils/ # Utility classes
└── SyfetaskApplication.java # Main entry point


## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Security:** spring-boot-starter-security
- **Database:** H2
- **Build Tool:** Maven
- **API Docs:** Swagger/OpenAPI
