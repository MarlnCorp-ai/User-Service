package marln.corp.ai.service.marln_user_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception class for all custom exceptions in the Marln User Service
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    private final String errorCode;
    
    public BaseException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
    
    public BaseException(String message, HttpStatus httpStatus, String errorCode, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
} 