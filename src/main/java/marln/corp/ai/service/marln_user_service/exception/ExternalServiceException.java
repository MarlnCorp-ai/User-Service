package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when external service communication fails
 */
public class ExternalServiceException extends BaseException {
    
    public ExternalServiceException(String serviceName, String message) {
        super("External service " + serviceName + " error: " + message, 
              HttpStatus.SERVICE_UNAVAILABLE, 
              "EXTERNAL_SERVICE_ERROR");
    }
    
    public ExternalServiceException(String serviceName, String message, Throwable cause) {
        super("External service " + serviceName + " error: " + message, 
              HttpStatus.SERVICE_UNAVAILABLE, 
              "EXTERNAL_SERVICE_ERROR", 
              cause);
    }
    

} 