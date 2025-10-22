# BloggingProject 📝

A lightweight Spring Boot blogging application that demonstrates a full CRUD (Create, Read, Update, Delete) flow for articles. This project uses Spring Data JPA, Thymeleaf templates, and UUID primary keys stored as `BINARY(16)`.

This repository is a small example project intended for learning and demo purposes.

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot** (Web, Thymeleaf, Spring Data JPA)
- **Maven**
- **MySQL** (JDBC-compatible database)

---

## ✨ Key Features

- **Full CRUD:** Create, Read, Update, and Delete articles.
- **Efficient UUIDs:** Article entity uses `java.util.UUID` as a primary key, which is stored as `BINARY(16)` in the database for high performance.
- **JPA Repository:** Uses Spring Data JPA for all database access.
- **Server-Side Rendering:** Uses Spring MVC (`BlogController`) to serve Thymeleaf templates.
- **Form Method Override:** Configured to support `PUT` and `DELETE` requests directly from HTML forms.

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
│   │       │   └── Article.java                 (JPA entity, maps UUID ID to BINARY(16))
│   │       └── repository/
│   │           └── BlogRepository.java          (Spring Data JPA repository interface)
│   │
│   └── resources/
│       ├── application.properties             (Application configuration)
│       └── templates/                         (Thymeleaf templates)
│           ├── index.html
│           ├── add.html
│           ├── update.html
│           └── fragments/
│               ├── fragment.html
│               └── layout.html
└── test/
```

---

## 📋 Prerequisites

- Java 21 JDK
- Maven (or use the included `mvnw` wrapper)
- A running relational database (e.g., MySQL)

---

## ⚙️ Configuration

The application reads its database configuration from environment variables.

1. **Use a `.env` file**
     For local development, you can create a `.env` file in the project root. `application.properties` in this project is configured to read from it.

     Example `.env`:

```env
DB_URL=jdbc:mysql://localhost:3306/blogdb?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASS=your_secret_password
```

2. **Database Schema**
    This project does not automatically create the schema by default (as `spring.jpa.hibernate.ddl-auto` is set to `none`). You must create the `articles` table in your database first.

```sql
CREATE TABLE articles (
    id BINARY(16) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

> **Note:** If you want Hibernate to automatically create or update the schema for you (e.g., during development), you can change the `spring.jpa.hibernate.ddl-auto` property in `application.properties` to `update`.

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
