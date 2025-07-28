package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when access is unauthorized
 */
public class UnauthorizedAccessException extends BaseException {
    
    public UnauthorizedAccessException() {
        super("Access denied. Insufficient permissions.", 
              HttpStatus.FORBIDDEN, 
              "UNAUTHORIZED_ACCESS");
    }
    
    public UnauthorizedAccessException(String message) {
        super(message, HttpStatus.FORBIDDEN, "UNAUTHORIZED_ACCESS");
    }
    
    public UnauthorizedAccessException(String message, String errorCode) {
        super(message, HttpStatus.FORBIDDEN, errorCode);
    }
} 