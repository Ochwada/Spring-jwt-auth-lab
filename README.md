 #  🔐 JWT Auth Lab: Securing a Dummy Controller with Spring Boot

SpringJWTAuthLab is a lightweight Spring Boot application designed to help understand how to secure REST APIs using 
JWT (JSON Web Tokens) and Spring Security. It focuses on protecting a dummy controller without requiring a full database 
setup or complex user management.

This lab is ideal for beginners exploring how stateless authentication works in modern Java applications. 
I built and test endpoints with authentication and authorization layers, all while learning how JWT integrates with 
Spring Security in a simple and approachable way.



## ✨ Features

A **minimal Spring Boot application** that includes:

-  A **hardcoded user** (no database required)
-  A `/login` endpoint that returns a **JWT** on successful login
-  A protected `/api/secure/hello` endpoint that requires a **valid JWT**
-  An optional `/api/secure/admin` endpoint accessible only to users with the **ADMIN** role

## 📦 Project Structure

```yaml
SpringJWTAuthLab/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.ochwada.jwt_starter_kit/
│   │   │       ├── config/                  # Security configuration (JWT filter, CORS, etc.)
│   │   │       ├── controller/              # Auth + secure API endpoints
│   │   │       ├── model/                   # User and Role classes
│   │   │       ├── service/                 # Authentication logic and JWT utilities
│   │   │       └── SpringJWTAuthLabApplication.java
│   │   └── resources/
│   │       └── application.properties       # JWT secret, port, etc.
├── build.gradle                             # Gradle dependencies and plugins
├── README.md                                # This file

```

## Tech Stack

This project uses the following technologies and tools:

#### Spring Boot
Simplifies Java web development with built-in configurations and dependency management.
> Used to build and run the REST API.

#### Spring Security
Handles authentication and authorization in Java applications.
> Used to secure endpoints, manage user roles, and integrate with JWT.

#### JWT (JSON Web Tokens)
A stateless way to securely transmit user information.
> Used to authenticate and authorize requests without storing session data.

#### Maven
A popular Java build and dependency management tool.
> Used to manage libraries like Spring Boot, Spring Security, and JWT.

#### Java 17
A long-term support (LTS) version of Java with modern features.
> Used for writing clean and efficient code compatible with Spring Boot 3.x.


##  Goals for the project

1. Understand the flow of stateless JWT authentication 
2. Learn how Spring Security filters handle login and token validation 
3. Practice securing REST endpoints with roles and permissions 
4. Use Postman or curl to test JWT-protected routes