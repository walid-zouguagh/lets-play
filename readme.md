## 1.1 Project Directory Structure

    A clean Spring Boot structure usually follows the Layered Architecture. Here is how we will organize the src/main/java directory:

    * config: Security (JWT), MongoDB, and general beans.

    * controller: REST endpoints.

    * dto: Data Transfer Objects (request/response models).

    * exception: Custom exceptions and the Global Exception Handler.

    * model: MongoDB entities (User, Product).

    * repository: Spring Data MongoDB repositories.

    * service: Business logic and interfaces.

    * security: JWT filters, providers, and UserDetailsService.

## To start the database:

```bash
docker compose up -d
```

```bash
docker ps
```

```bash
./mvnw clean compile
```

```bash
./mvnw spring-boot:run
```

```bash
docker exec -it lets-play-db mongosh -u admin -p password123 --authenticationDatabase admin

use letsplay_db

show collections

db.users.find().pretty()
db.products.find().pretty()
```

```bash
db.users.find().pretty()


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
