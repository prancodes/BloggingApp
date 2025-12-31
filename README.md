# BloggingProject 📝

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.1-green)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A lightweight Spring Boot blogging application that demonstrates a full CRUD (Create, Read, Update, Delete) flow for articles. This project uses Spring Data JPA, Thymeleaf templates, and UUID primary keys stored as `BINARY(16)`.

This is a learning project showcasing best practices in Spring Boot development.

---

🚀 **Live Link:** [BlogSpace](https://blogspace24.onrender.com/)

---

## 🛠️ Tech Stack

- **Java 25** (LTS)
- **Spring Boot 4.0.1** (Web, Thymeleaf, Data JPA, Validation)
- **Maven** (Build tool)
- **MySQL 8.0** (JDBC-compatible database)
- **Thymeleaf** (Server-side HTML templates)
- **Docker & Docker Compose** (Containerization)

---

## ✨ Key Features

- **Complete CRUD Operations:** Create, read, update, and delete articles through an intuitive web interface.
- **UUID Primary Keys:** Uses Java `UUID` with Hibernate's `GenerationType.UUID` strategy, optimized as `BINARY(16)` storage for database performance.
- **Input Validation:** Client-side and server-side validation using Jakarta validation annotations (`@NotBlank`, `@Size`).
- **Automatic Timestamps:** Tracks article creation and modification times with `@CreationTimestamp` and `@UpdateTimestamp`.
- **Server-Side Rendering:** Built with Spring MVC and Thymeleaf templates for dynamic HTML generation.
- **Exception Handling:** Global exception handler for graceful error management and user feedback.
- **Profile-Based Configuration:** Separate environment configurations for development and production.
- **Production Keep-Alive Service:** Scheduled pinging mechanism to keep the app alive on free hosting platforms (production profile only).

---

## 📂 Project Structure


```
src/
├── main/
│   ├── java/
│   │   └── com/blog/project/
│   │       ├── BloggingProjectApplication.java  (Application entry point)
│   │       ├── controller/
│   │       │   └── BlogController.java          (Spring MVC controller for web requests)
│   │       ├── model/
│   │       │   └── Article.java                 (JPA entity with UUID ID and timestamps)
│   │       ├── repository/
│   │       │   └── BlogRepository.java          (Spring Data JPA repository)
│   │       ├── service/
│   │       │   └── KeepAliveService.java        (Scheduled task for production)
│   │       └── exception/
│   │           ├── ArticleNotFoundException.java (Custom exception)
│   │           └── GlobalExceptionHandler.java  (Centralized error handling)
│   │
│   └── resources/
│       ├── application.properties             (Main configuration)
│       ├── application-dev.properties         (Development overrides)
│       ├── application-prod.properties        (Production overrides)
│       ├── static/
│       │   └── validation.js                  (Client-side validation scripts)
│       └── templates/                         (Thymeleaf templates)
│           ├── index.html                     (Article list view)
│           ├── add.html                       (Create article form)
│           ├── update.html                    (Edit article form)
│           ├── fragments/
│           │   ├── fragment.html              (Reusable UI components)
│           │   └── layout.html                (Base layout template)
│           └── error/
│               └── error-message.html         (Error page template)
└── test/
    └── java/
        └── com/blog/project/
            └── BloggingProjectApplicationTests.java
```

---

## 🔧 Setup Instructions

### Option 1: Run with Docker (Recommended)
If you have Docker installed, you can spin up the Application and Database instantly.

```bash
docker-compose up --build
```
The app will be available at http://localhost:8080.

---

### Option 2: Run Manually

 ⚙️ Configuration

The application uses environment variables for database credentials and profile-specific configuration.

1. **Set Environment Variables**
   
   Create a `.env` file in the project root or set these environment variables in your system:

```env
SPRING_PROFILES_ACTIVE=dev
DB_URL=jdbc:mysql://localhost:3306/blogdb?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASS=your_secret_password
```

2. **Database Schema**
    This project does not automatically create the schema by default (as `spring.jpa.hibernate.ddl-auto` is set to `none`). You must create the `articles` table in your database first.

```sql
CREATE DATABASE blogdb;

CREATE TABLE articles (
    id BINARY(16) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

> **Note:** If you want Hibernate to automatically create or update the schema for you (e.g., during development), you can change the `spring.jpa.hibernate.ddl-auto` property in `application-dev.properties` to `update`.

---

## 🚀 Getting Started

### 1. Build the Project

From the project root, build the application using the Maven wrapper.

**On Windows (cmd.exe):**
```cmd
mvnw.cmd clean package
```

**On macOS / Linux:**
```bash
./mvnw clean package
```

### 2. Run the Application

Run with the Spring Boot plugin (development):

**On Windows (cmd.exe):**
```cmd
mvnw.cmd spring-boot:run
```

**On macOS / Linux:**
```bash
./mvnw spring-boot:run
```

Or run the compiled jar after building:

```cmd
java -jar target/project-0.0.1-SNAPSHOT.jar
```

The application will be available at http://localhost:8080 (unless you changed the port in `application.properties`).

---

## 🧪 Testing

Run tests with the wrapper:

```cmd
mvnw.cmd test
```

Or on macOS / Linux:

```bash
./mvnw test
```

---

<p align="center"> Developed and Maintained by <strong>Pranjal Singh</strong> </p>