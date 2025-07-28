package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when data integrity is violated
 */
public class DataIntegrityException extends BaseException {
    
    public DataIntegrityException(String message) {
        super(message, HttpStatus.CONFLICT, "DATA_INTEGRITY_VIOLATION");
    }
    
    public DataIntegrityException(String message, String errorCode) {
        super(message, HttpStatus.CONFLICT, errorCode);
    }
} 