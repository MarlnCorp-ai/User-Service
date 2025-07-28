package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when JWT token is invalid or expired
 */
public class InvalidTokenException extends BaseException {
    
    public InvalidTokenException() {
        super("Invalid or expired token", 
              HttpStatus.UNAUTHORIZED, 
              "INVALID_TOKEN");
    }
    
    public InvalidTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
    }
    
    public InvalidTokenException(String message, String errorCode) {
        super(message, HttpStatus.UNAUTHORIZED, errorCode);
    }
} 