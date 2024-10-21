# SecurityApp
## Features

- User Registration: New users can register with a username, password, and role.
- User Login: Users can log in with their credentials to receive a JWT token.
- Role-based Authorization: Access to certain endpoints is restricted based on user roles (e.g., ROLE_USER, ROLE_ADMIN).
- JWT Authentication: Secure authentication using JSON Web Tokens (JWT).
- User Management
  
## Dependencies

- Spring Boot: Core framework for building the application.
- Spring Security: Manages authentication and authorization.
- JWT (com.auth0:java-jwt): For generating and validating JWT tokens.
- PostgreSQL: Database for storing user information.
- ModelMapper: Maps DTOs to entities.
- Lombok.

 ## Run application: 
 - You can launch it through intellij idea (just run project)
 - Run it through docker: path/docker-compose up --build
 - Testing: postman

## Ports:
- application: 8080
- database: 5432

## For testing import postman collection TestTask.postman_collection.json 

## API Endpoints
### GET /api/users
- Fetches a list of all users.
- Authorization: Requires ROLE_USER or ROLE_ADMIN

### POST /auth/register
- Request Body: 
  ```json
  [
    {
      "username": "john_doe",
      "password": "password",
      "role": "ROLE_USER"
    },
    {
      "username": "admin",
      "password": "password",
      "role": "ROLE_ADMIN"
    }
  ]
- Response:
   ```json
   {
    "message": "User already exists with username: john_doe",
    "timestamp": 1729518336024
   }

### POST /auth/login
- Description: Logs in a user and returns a JWT token.
- Request Body:
  ```json
  {
    "username": "john_doe",
    "password": "password"
  }

- Response:
  ```json
  {
    "jwt-token": "your_jwt_token_here"
  }

  
### Error Handling
 - If the login credentials are invalid, an InvalidLoginException is thrown with a message:
- Response:
  ```json
  {
   "error": "Invalid username or password"
  }


### DELETE /api/users/{id}
- Description: Deletes a user by their ID.
- Authorization: Requires ROLE_ADMIN.
- Response:
  ```json
  {
    "message": "User deleted successfully"
  }

### Error Handling
If the user is not found, a PersonNotFoundException will be thrown with a message:
```json
{
  "error": "User not found",
  "status": 404
}
