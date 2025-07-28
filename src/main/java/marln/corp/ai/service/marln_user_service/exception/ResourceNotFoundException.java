package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Generic exception thrown when a resource is not found
 */
public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(resourceType + " not found with id: " + resourceId, 
              HttpStatus.NOT_FOUND, 
              "RESOURCE_NOT_FOUND");
    }
    

} 