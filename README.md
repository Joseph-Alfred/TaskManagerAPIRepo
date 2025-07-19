# TaskManagerAPIRepo

# 📝 Task Management API

A Spring Boot RESTful API for managing tasks.  
Built as part of a backend development assignment for Airtribe.

---

## 🚀 Features

- Create, retrieve, update, and delete tasks (CRUD)
- Bean validation using Hibernate Validator
- Exception handling with meaningful error responses
- H2 in-memory database with web console
- DTO-based architecture
- Unit & integration testing with JUnit and Mockito

---

## 🧱 Tech Stack

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Web, Spring Data JPA**
- **H2 Database**
- **Lombok, DevTools, Validation**
- **JUnit 5, Mockito**
- **Postman for API testing**

---

## 📦 Project Structure

com.alfred.taskmanager
├── controller # REST APIs
├── service # Business logic
├── repository # JPA Repository
├── entity # Task entity
├── dto # Request/response DTOs
├── enums # Status & Priority enums
├── exception # Custom & global error handlers
└── TaskManagerApplication.java


---

## 🔌 API Endpoints

| Method | Endpoint          | Description        |
|--------|-------------------|--------------------|
| `POST` | `/api/tasks`      | Create a new task  |
| `GET`  | `/api/tasks`      | Get all tasks      |
| `GET`  | `/api/tasks/{id}` | Get task by ID     |
| `PUT`  | `/api/tasks/{id}` | Update task by ID  |
| `DELETE` | `/api/tasks/{id}` | Delete task by ID  |

---

## 🧪 Sample Request (POST)

```http
POST /api/tasks
Content-Type: application/json

{
  "title": "Finish Assignment",
  "description": "Implement and test the API",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-08-01T12:00:00"
}
