# TeamWork Microservices API

This is a RESTful backend application built with **Java, Spring Boot, and Spring Cloud**, following a microservices architecture.

TeamWork provides an internal platform where employees can create articles, upload GIFs, comment on shared content, and view posts through a unified feed.

The application is divided into independently running services that communicate through REST APIs and use Netflix Eureka for service discovery.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Tech Stack](#tech-stack)
3. [System Architecture](#system-architecture)
4. [Microservices](#microservices)
5. [Project Structure](#project-structure)
6. [Authentication and Authorization](#authentication-and-authorization)
7. [Prerequisites](#prerequisites)
8. [Configuration](#configuration)
9. [Running the Application](#running-the-application)
10. [API Documentation](#api-documentation)
11. [Inter-Service Communication](#inter-service-communication)

## Project Overview

TeamWork is an employee collaboration backend designed to enable communication and content sharing within an organization.

The application supports:

- User registration and authentication
- Role-based authorization
- Article creation and management
- GIF uploads and management
- Commenting on articles and GIFs
- Aggregation of articles and GIFs into a unified feed
- Service discovery and communication between microservices

Each service handles a specific business responsibility, allowing its functionality to be developed, maintained, and deployed independently.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | REST API development |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database persistence |
| Spring Cloud Netflix Eureka | Service discovery |
| Spring Cloud LoadBalancer | Service-to-service request resolution |
| JWT | Token-based authentication |
| MySQL | Relational database |
| Maven | Dependency management and builds |
| Cloudinary | GIF and image storage |
| Swagger / OpenAPI | API documentation |
| Lombok | Boilerplate code reduction |
| MapStruct | DTO and entity mapping |
| Jakarta Bean Validation | Request validation |

## System Architecture

The application consists of five independently running Spring Boot applications.

The services communicate using synchronous HTTP requests, with Eureka providing service registration and discovery.

### High-Level Architecture

- **User Service:** Manages users, authentication, and authorization.
- **Article Service:** Manages articles and article comments.
- **GIF Service:** Manages GIF uploads and GIF comments.
- **Feed Service:** Aggregates content from the Article and GIF services.
- **Service Discovery:** Registers service instances and enables service discovery.

The Article Service and GIF Service communicate with the User Service when user information is required.

The Feed Service communicates with the Article Service and GIF Service to retrieve content for the unified feed.

## Microservices

### 1. User Service

**Default Port:** `8085`
**Swagger url:** `http://localhost:8085/swagger-ui/index.html`


Responsible for user management, authentication, and authorization.

Responsibilities:

- Register users
- Authenticate users
- Generate JWT access tokens
- Retrieve user information
- Manage user roles

The application defines two user roles:

- `ADMIN`
- `EMPLOYEE`

The User Service acts as the authentication provider for the application.

### 2. Article Service

**Default Port:** `8086`
**Swagger url:** `http://localhost:8086/swagger-ui/index.html`

Responsible for article-related operations.

Responsibilities:

- Create articles
- Retrieve articles
- Update existing articles
- Delete articles
- Create and retrieve article comments

The service uses JWT authentication to identify users making requests.

It also communicates with the User Service to retrieve user information required for certain operations.

### 3. GIF Service

**Default Port:** `8087`
**Swagger url:** `http://localhost:8087/swagger-ui/index.html`


Responsible for GIF-related operations and media uploads.

Responsibilities:

- Upload GIFs and images
- Store uploaded files using Cloudinary
- Retrieve GIFs
- Delete GIFs
- Create and retrieve GIF comments

GIF uploads use `multipart/form-data`.

The service integrates with Cloudinary to store uploaded media and persist the resulting URL in the database.

### 4. Feed Service

**Default Port:** `8089`
**Swagger url:** `http://localhost:8089/swagger-ui/index.html`


Responsible for aggregating content from other services.

Responsibilities:

- Retrieve articles from the Article Service
- Retrieve GIFs from the GIF Service
- Provide a unified content feed
- Support paginated content retrieval

The Feed Service uses REST-based inter-service communication.

### 5. Service Discovery

**Default Port:** `8761`

Uses Netflix Eureka Server for service registration and discovery.

Each microservice registers itself with Eureka, allowing other services to resolve it by its application name rather than relying on hardcoded host addresses.

Eureka Dashboard:

`http://localhost:8761`

## Project Structure

```text
teamwork-microservices-api/
│
├── article-service/
│   ├── src/
│   ├── pom.xml
│   └── mvnw
│
├── feed-service/
│   ├── src/
│   ├── pom.xml
│   └── mvnw
│
├── gif-service/
│   ├── src/
│   ├── pom.xml
│   └── mvnw
│
├── teamwork-service-discovery/
│   ├── src/
│   ├── pom.xml
│   └── mvnw
│
├── user-service/
│   ├── src/
│   ├── pom.xml
│   └── mvnw
│
└── README.md
```

Each service maintains its own Spring Boot application, dependencies, configuration, and business logic.

## Authentication and Authorization

The application uses JSON Web Tokens (JWT) and Spring Security to secure protected endpoints.

### Authentication Flow

1. A user submits their credentials to the User Service.
2. The User Service authenticates the credentials.
3. A JWT access token is generated and returned.
4. The client includes the token in subsequent protected API requests.
5. The receiving service validates the token before processing the request.

### Authorization Header

Protected requests must include the following header:

```http
Authorization: Bearer <JWT_TOKEN>
```

### Role-Based Access Control

The application supports `ADMIN` and `EMPLOYEE` roles.

Authorization rules are enforced according to the operations available to each role.

### Token Propagation

When a service needs to communicate with another protected service on behalf of a user, it forwards the incoming Authorization header.

For example, the Article Service may retrieve user information from the User Service while processing an authenticated request.

## Prerequisites

Before running the application locally, ensure the following tools are installed:

- Java JDK compatible with the configured Spring Boot version
- Maven, or the Maven wrapper included with each service
- MySQL Server
- Git
- An IDE such as IntelliJ IDEA
- A Cloudinary account for media uploads

Postman or Swagger UI can be used to test the REST APIs.

## Configuration

Each microservice has its own configuration file, typically located at:

```text
src/main/resources/application.yml
```

### Database Configuration

Configure the datasource for each service that requires database persistence.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/<database_name>
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Create the required databases before starting the services.

The database names and schema initialization settings must match the configuration of each service.

### JWT Configuration

Services responsible for generating or validating JWTs require the appropriate security configuration.

Example property names used by the application:

```properties
security.jwt.secret-key=${JWT_SECRET_KEY}
security.jwt.expiration-time=${JWT_EXPIRATION_TIME}
```

JWT configuration must be consistent across the services that validate the tokens.

### Eureka Client Configuration

The microservices register with Eureka using the service discovery URL.

Example:

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

Each service must also have a unique application name.

### Cloudinary Configuration

The GIF Service requires Cloudinary credentials for media uploads.

```properties
api-key=${API_KEY}
api-secret=${API_SECRET}
cloud-name=${CLOUD_NAME}
```

## Running the Application

### Step 1: Clone the Repository

```bash
git clone https://github.com/steverado9/teamwork-microservice-api.git
```

Navigate to the project directory:

```bash
cd teamwork-microservices-api
```

### Step 2: Configure the Databases

Create the required MySQL databases.

Update each service's configuration with the correct database connection settings.

### Step 3: Start the Eureka Discovery Server

Open a terminal in the discovery server directory:

```bash
cd teamwork-service-discovery
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Once started, visit:

`http://localhost:8761`

Verify that the Eureka dashboard is accessible.

### Step 4: Start the User Service

Open a separate terminal:

```bash
cd user-service
```

Run:

```powershell
.\mvnw.cmd spring-boot:run
```

The service is configured to run on port `8085`.

### Step 5: Start the Article Service

Open a separate terminal:

```bash
cd article-service
```

Run:

```powershell
.\mvnw.cmd spring-boot:run
```

The service is configured to run on port `8086`.

### Step 6: Start the GIF Service

Open a separate terminal:

```bash
cd gif-service
```

Run:

```powershell
.\mvnw.cmd spring-boot:run
```

The service is configured to run on port `8087`.

### Step 7: Start the Feed Service

Open a separate terminal:

```bash
cd feed-service
```

Run:

```powershell
.\mvnw.cmd spring-boot:run
```

The service is configured to run on port `8089`.

### Step 8: Verify Service Registration

Open:

`http://localhost:8761`

Verify that the expected microservices appear in the registered instances list and have an `UP` status.

## API Documentation

The project uses Springdoc OpenAPI and Swagger UI for interactive API documentation.

For Swagger UI, the conventional URL for user-service, article-service, gif-service and feed-service is:

```text
http://localhost:8085/swagger-ui/index.html
http://localhost:8086/swagger-ui/index.html
http://localhost:8087/swagger-ui/index.html
http://localhost:8089/swagger-ui/index.html
```

The OpenAPI JSON document is conventionally available at:

```text
http://localhost:<service-port>/v3/api-docs
```

### Authentication in Swagger

To test protected endpoints:

1. Authenticate through the User Service.
2. Copy the returned JWT token.
3. Open Swagger UI for the target service.
4. Click **Authorize**.
5. Provide the token using the configured Bearer authentication scheme.
6. Execute the desired endpoint.

## Inter-Service Communication

The application uses Spring's `RestTemplate` for synchronous communication between services.

A load-balanced RestTemplate allows service names registered in Eureka to be used when constructing requests.

Example:

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

A service can then make requests using a registered service name:

```text
http://user-service/auth/{id}
```

Instead of directly referencing a fixed host and port.

### Secured Service Requests

For endpoints that require authentication, the calling service must propagate the appropriate Authorization header.

Example:

```java
HttpHeaders headers = new HttpHeaders();
headers.set(
    HttpHeaders.AUTHORIZATION,
    authorizationHeader
);

HttpEntity<Void> entity = new HttpEntity<>(headers);

ResponseEntity<User> response = restTemplate.exchange(
    "http://user-service/auth/" + userId,
    HttpMethod.GET,
    entity,
    User.class
);
```

This allows the downstream service to validate the token and apply its security rules.

## Development Notes

### Service Independence

Each microservice is maintained as a separate Spring Boot application with its own Maven dependencies and configuration.

### Database Ownership

Database persistence is handled within the relevant service. Services should communicate through defined APIs rather than directly accessing another service's database.

### Error Handling

The services use application-specific exceptions and structured API responses for error handling.

### Request Validation

Jakarta Bean Validation is used to validate incoming request data before business logic is executed.

### Media Storage

The GIF Service uses Cloudinary to store uploaded files and maintains references to the uploaded media.


