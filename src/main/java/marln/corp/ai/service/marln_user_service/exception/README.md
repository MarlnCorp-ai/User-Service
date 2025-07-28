# Custom Exception System

This package contains a comprehensive custom exception hierarchy for the Marln User Service application.

## Exception Hierarchy

### Base Exception
- **BaseException**: Abstract base class for all custom exceptions
  - Contains HTTP status, error code, and message
  - All custom exceptions extend this class

### Specific Exceptions

#### User Management
- **UserNotFoundException**: Thrown when a user is not found
- **UserAlreadyExistsException**: Thrown when trying to create a user with existing email

#### Employee Management
- **EmployeeNotFoundException**: Thrown when an employee is not found

#### Authentication & Authorization
- **InvalidCredentialsException**: Thrown for invalid login credentials
- **InvalidTokenException**: Thrown for invalid or expired JWT tokens
- **UnauthorizedAccessException**: Thrown for insufficient permissions

#### Validation
- **ValidationException**: Thrown when validation fails

#### External Services
- **ExternalServiceException**: Thrown when external service communication fails

#### Data Integrity
- **DataIntegrityException**: Thrown for data integrity violations

#### Generic
- **ResourceNotFoundException**: Generic exception for any resource not found

## Global Exception Handler

The `GlobalExceptionHandler` class provides centralized exception handling:

- Handles all custom `BaseException` instances
- Provides consistent error response format
- Logs exceptions for debugging
- Handles validation errors from `@Valid` annotations
- Handles Spring Security exceptions
- Provides fallback for unexpected exceptions

## Error Response Format

All exceptions return a consistent error response:

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 123",
  "errorCode": "USER_NOT_FOUND",
  "path": "/api/user/123"
}
```

## Usage Examples

### Throwing Custom Exceptions

```java
// User not found
if (!userRepository.existsById(userId)) {
    throw new UserNotFoundException(userId);
}

// User already exists
if (userRepository.findByEmail(email).isPresent()) {
    throw new UserAlreadyExistsException(email);
}

// Invalid credentials
if (!passwordEncoder.matches(password, user.getPasswordHash())) {
    throw new InvalidCredentialsException();
}

// External service error
try {
    externalService.call();
} catch (Exception ex) {
    throw new ExternalServiceException("Service Name", "Operation failed", ex);
}
```

### Error Codes

Use the constants from `ExceptionConstants` for consistent error codes:

```java
throw new ValidationException("Invalid input", ExceptionConstants.INVALID_INPUT);
```

## Best Practices

1. **Use specific exceptions**: Choose the most specific exception for the error condition
2. **Include meaningful messages**: Provide clear, user-friendly error messages
3. **Use error codes**: Include error codes for client-side error handling
4. **Log exceptions**: The global handler automatically logs all exceptions
5. **Handle external services**: Always wrap external service calls with proper exception handling
6. **Validate inputs**: Use validation exceptions for input validation errors

## HTTP Status Codes

- **400 Bad Request**: Validation errors, invalid input
- **401 Unauthorized**: Invalid credentials, invalid tokens
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resources not found
- **409 Conflict**: Data integrity violations, duplicate entries
- **503 Service Unavailable**: External service failures
- **500 Internal Server Error**: Unexpected errors

## Adding New Exceptions

To add a new exception:

1. Create a new class extending `BaseException`
2. Add appropriate constructors
3. Use appropriate HTTP status code
4. Add error code constant to `ExceptionConstants`
5. Update this documentation

Example:
```java
public class NewCustomException extends BaseException {
    public NewCustomException(String message) {
        super(message, HttpStatus.BAD_REQUEST, ExceptionConstants.NEW_ERROR_CODE);
    }
}
``` 