# Bug Tracking System - Microservices Architecture

## Overview
This is a comprehensive bug tracking system built using a microservices architecture. The system is designed to help teams track, manage, and resolve software bugs efficiently. It provides features for bug reporting, assignment, tracking, and collaboration through comments.

## Architecture

### System Components

1. **API Gateway** (`APIgateway/`)
   - Entry point for all client requests
   - Routes requests to appropriate microservices
   - Handles cross-cutting concerns like authentication and CORS
   - Port: 8765

2. **Service Registry** (`ServiceRegistry/`)
   - Implements service discovery using Spring Cloud Eureka
   - Helps microservices locate and communicate with each other
   - Maintains service health and availability

3. **User Service** (`UserService/`)
   - Manages user authentication and authorization
   - Handles user registration and login
   - JWT token generation and validation
   - User profile management
   - Role-based access control (ADMIN, DEVELOPER, TESTER)
   - Cross-cutting concerns:
     - Logging: Method entry/exit and return values
     - Performance: Execution time monitoring
     - Security: Authentication context logging

4. **Bug Service** (`BugService/`)
   - Core bug tracking functionality
   - Bug creation, update, and deletion
   - Bug status and priority management
   - Bug assignment to developers
   - Bug filtering and search capabilities
   - Cross-cutting concerns:
     - Logging: Method entry/exit and return values
     - Performance: Execution time monitoring
     - Security: Authentication context logging

5. **Comment Service** (`CommentService/`)
   - Manages comments on bugs
   - Enables team collaboration
   - Comment creation and deletion
   - Comment retrieval by bug ID
   - Cross-cutting concerns:
     - Logging: Method entry/exit and return values
     - Performance: Execution time monitoring
     - Security: Authentication context logging

### Technology Stack

- **Backend Framework**: Spring Boot
- **Security**: Spring Security with JWT
- **Service Discovery**: Spring Cloud Eureka
- **API Gateway**: Spring Cloud Gateway
- **Database**: (To be specified)
- **Build Tool**: Maven
- **Language**: Java

## Features

### User Management
- User registration and authentication
- Role-based access control
- JWT token-based authentication
- Token refresh mechanism
- User profile management

### Bug Management
- Create new bugs with detailed information
- Update bug status and details
- Assign bugs to developers
- Set bug priority levels
- Track bug status
- Filter bugs by:
  - Priority
  - Status
  - Developer
  - Tester
  - Bug ID

### Comment System
- Add comments to bugs
- Delete comments
- View comments by bug ID
- Delete all comments for a bug

### Security Features
- JWT-based authentication
- Role-based authorization
- CORS configuration
- Secure endpoints
- Token refresh mechanism

## API Endpoints

### User Service
```
POST /register - Register new user
POST /login - User login
GET /refreshtoken - Get new access token
GET /username/{id} - Get username by ID
GET /user/{username} - Get user details
```

### Bug Service
```
POST /bugs/create - Create new bug
PUT /bugs/update - Update bug
GET /bugs/by_priority/{priority} - Get bugs by priority
GET /bugs/by_status/{status} - Get bugs by status
GET /bugs/developer_id/{id} - Get bugs by developer
GET /bugs/tester_id/{id} - Get bugs by tester
GET /bugs/bug_id/{id} - Get bug by ID
GET /bugs/all - Get all bugs
DELETE /bugs/delete/{id} - Delete bug
```

### Comment Service
```
GET /comments/by_bug/{bugId} - Get comments by bug ID
POST /comments/create - Create new comment
DELETE /comments/delete/{id} - Delete comment
DELETE /comments/delete_by_bug/{id} - Delete all comments for a bug
```

## Authorization Rules

### User Roles
1. **ADMIN**
   - Full access to all features
   - Can delete bugs and comments
   - Can manage users

2. **DEVELOPER**
   - Can view and update bugs
   - Can add comments
   - Can view assigned bugs

3. **TESTER**
   - Can create new bugs
   - Can add comments
   - Can view bugs

## Security Configuration

### CORS Configuration
- Allowed Origin: http://localhost:8083
- Allowed Methods: All
- Allowed Headers: All
- Credentials: Enabled
- Preflight Cache: 1 hour

### JWT Configuration
- Token-based authentication
- Token refresh mechanism
- Role-based authorization
- Secure endpoints

## Cross-Cutting Concerns (AOP)

Each service implements three types of aspects:

### 1. LoggingAspect
- Tracks method entry and exit points
- Logs method return values
- Applies to both controller and service layers
- Provides detailed execution flow logging

### 2. PerformanceAspect
- Measures method execution time
- Warns about slow executions (over 1 second)
- Applies to service layer methods
- Helps identify performance bottlenecks

### 3. SecurityAspect
- Logs security context for controller methods
- Tracks authenticated and unauthenticated access
- Provides security audit trail
- Monitors unauthorized access attempts

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- (Database details to be added)

### Running the Services
1. Start Service Registry
2. Start API Gateway
3. Start User Service
4. Start Bug Service
5. Start Comment Service

### Development Setup
1. Clone the repository
2. Configure database properties
3. Build each service using Maven
4. Run services in the correct order

## Best Practices
1. Always use HTTPS in production
2. Implement proper error handling
3. Use appropriate logging
4. Follow microservices best practices
5. Implement circuit breakers for service resilience
6. Use proper exception handling
7. Implement proper validation

## License
(MIT LICENSE)