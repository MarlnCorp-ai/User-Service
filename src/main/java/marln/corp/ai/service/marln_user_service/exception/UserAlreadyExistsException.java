package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user already exists
 */
public class UserAlreadyExistsException extends BaseException {
    
    public UserAlreadyExistsException(String email) {
        super("User already exists with email: " + email, 
              HttpStatus.CONFLICT, 
              "USER_ALREADY_EXISTS");
    }
    
    public UserAlreadyExistsException(String message, String errorCode) {
        super(message, HttpStatus.CONFLICT, errorCode);
    }
} 