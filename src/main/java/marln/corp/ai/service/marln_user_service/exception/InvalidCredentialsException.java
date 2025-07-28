package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when login credentials are invalid
 */
public class InvalidCredentialsException extends BaseException {
    
    public InvalidCredentialsException() {
        super("Invalid email or password", 
              HttpStatus.UNAUTHORIZED, 
              "INVALID_CREDENTIALS");
    }
    
    public InvalidCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
    }
    
    public InvalidCredentialsException(String message, String errorCode) {
        super(message, HttpStatus.UNAUTHORIZED, errorCode);
    }
} 