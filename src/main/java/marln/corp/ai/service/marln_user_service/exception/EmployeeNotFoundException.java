package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an employee is not found
 */
public class EmployeeNotFoundException extends BaseException {
    
    public EmployeeNotFoundException(String employeeId) {
        super("Employee not found with id: " + employeeId, 
              HttpStatus.NOT_FOUND, 
              "EMPLOYEE_NOT_FOUND");
    }
    
    public EmployeeNotFoundException(String message, String errorCode) {
        super(message, HttpStatus.NOT_FOUND, errorCode);
    }
} 