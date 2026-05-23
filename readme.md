# 🎮 Let's Play - Secure Backend REST API

A production-ready, highly secure Spring Boot REST API built with **Spring Boot 4.x**, **Spring Security**, and **Dockerized MongoDB**. This application handles fine-grained resource tracking, containerized state persistence, state-free JSON Web Token (JWT) authentication, and custom validation handling.

---

## 🚀 Key Features

* **📦 Containerized Database Layer:** Fully isolated MongoDB multi-tenant environment run via Docker.
* **🛡️ Stateful Filter Chain & JWT Engine:** Cryptographically signed tokens using HS256 containing identity context.
* **🔒 Strict Role Boundary & Isolation:** Securely configured endpoints ensuring only **one system-wide Admin** can exist, forcing all external user sign-ups to standard roles.
* **👤 Contextual Resource Ownership:** Built-in business checks logic so regular users can only alter documents they created, while Admins retain global administrative override rights.
* **🎯 Global Fault Management:** Graceful application interceptors preventing raw unhandled 5XX leaks by formatting uniform JSON structures down to clients.
* **🌐 Cross-Origin Support (CORS):** Fully calibrated multi-client browser resource policy supporting separate frontend frameworks.

---

## 🛠️ Technology Stack

* **Backend Engine:** Java 21 / Spring Boot 4.0.6
* **Security Framework:** Spring Security (Stateless JWT, BCrypt Hashing)
* **Database Management:** MongoDB (Stateful Multi-Collection Mapping)
* **Containerization:** Docker Engine / Docker Compose
* **Client Validation:** Postman API Suite / Terminal Shell (`mongosh`)

---

## 📂 Project Architecture Blueprint

```text
src/main/java/com/lets_play/
│
├── config/              # Security configurations & Cross-Origin rules
├── controller/          # REST Endpoint Controllers (Auth, Products, Users)
├── dto/                 # Unified Request/Response Payloads & Data Transfer Objects
├── exception/           # Global REST Advice Interceptors & Error Mapping
├── model/               # Document Entities mapped to MongoDB (User, Product)
├── repository/          # Reactive Mongo Database Interfaces
├── service/             # Granular Business Logic & Authorization Filters
└── LetsPlayApplication  # Main Bootloader & Automatic Database Initializer
```

## Setup & Installation Instructions:
1. Database Container Deployment
Spin up your fully isolated, credentialed MongoDB instance inside Docker:

```bash
docker run -d \
  --name lets-play-db \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=password123 \
  mongo:latest
```

2. Environment Configuration File
Verify your active system properties file located at src/main/resources/application.properties:
```bash 
spring.application.name=lets-play

# Deployment Server Listening Port
server.port=8080
server.error.include-message=always
```

3. Compilation & Runtime Execution
Wipe old compiled assets, force configuration profile reading, and boot up the server:
```bash
docker compose up -d
docker ps
./mvnw clean compile
./mvnw spring-boot:run
./mvnw clean compile spring-boot:run
```


## Terminal Database Debugging Guide
```bash
docker exec -it lets-play-db mongosh -u admin -p password123 --authenticationDatabase admin
```

```bash
use letsplay_db             // Switches over to project data layer context
show collections            // Displays active collections: [users, products]
db.users.find().pretty()    // Prints stored users alongside cryptographically hashed passwords
db.products.find().pretty() // Prints items tied to their creator email profiles
db.dropDatabase()           // Nukes database layer clean to force a brand new state rebuild
```

```bash
Count documents: See how many total users you have:
db.users.countDocuments()

Find a specific user: Search directly by email instead of scrolling:
db.users.findOne({ email: "user1@gmail.com" })


Clear the collection (Reset): If you want to delete all test users and start fresh:
db.users.deleteMany({})
```

## Data Flow Overview
    Now that the models and repositories are set, here is how the data will flow through your "Let's Play" application:

    * Request: A JSON object comes into the Controller.

    * Service: Logic ensures the user owns the product they are trying to edit.

    * Repository: Interacts with the Dockerized MongoDB.

    * Database: Stores the document in the products or users collection.

```bash 
* Public Access: GET /api/products works without a token.

* Auth: POST /api/auth/register and /login work.

* Security: POST /api/products requires the "Bearer" token.
```

## Database Seeding & Admin Policy
To protect system integrity, external clients cannot register as admins. Instead, the application seeds exactly one system-wide Administrator into MongoDB automatically upon structural startup via the system context loader:

* Admin Username: admin@gmail.com
* Admin Password: admin123
* Role Mapped: ROLE_ADMIN

## Postman API Verification Runbook
1. User Registration (POST)
URL: http://localhost:8080/api/auth/register

Body (JSON):
```bash 
{
    "name": "John Doe",
    "email": "johndoe@gmail.com",
    "password": "securepassword123"
}
```

2. Secure Authentication Login (POST)
URL: http://localhost:8080/api/auth/login

Body (JSON):

```bash 
{
    "email": "johndoe@gmail.com",
    "password": "securepassword123"
}
```