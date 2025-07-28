package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when validation fails
 */
public class ValidationException extends BaseException {
    
    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR");
    }
    
    public ValidationException(String message, String errorCode) {
        super(message, HttpStatus.BAD_REQUEST, errorCode);
    }
} 