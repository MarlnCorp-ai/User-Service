package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user is not found
 */
public class UserNotFoundException extends BaseException {
    
    public UserNotFoundException(String userId) {
        super("User not found with id: " + userId, 
              HttpStatus.NOT_FOUND, 
              "USER_NOT_FOUND");
    }
    
    public UserNotFoundException(String message, String errorCode) {
        super(message, HttpStatus.NOT_FOUND, errorCode);
    }
} 