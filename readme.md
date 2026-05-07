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
