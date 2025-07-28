package marln.corp.ai.service.marln_user_service.exception;

/**
 * Constants for error codes used throughout the application
 */
public final class ExceptionConstants {
    
    private ExceptionConstants() {
        // Private constructor to prevent instantiation
    }
    
    // User related error codes
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String USER_CREATION_FAILED = "USER_CREATION_FAILED";
    public static final String USER_UPDATE_FAILED = "USER_UPDATE_FAILED";
    public static final String USER_DELETION_FAILED = "USER_DELETION_FAILED";
    
    // Employee related error codes
    public static final String EMPLOYEE_NOT_FOUND = "EMPLOYEE_NOT_FOUND";
    public static final String EMPLOYEE_ALREADY_EXISTS = "EMPLOYEE_ALREADY_EXISTS";
    public static final String EMPLOYEE_CREATION_FAILED = "EMPLOYEE_CREATION_FAILED";
    public static final String EMPLOYEE_UPDATE_FAILED = "EMPLOYEE_UPDATE_FAILED";
    public static final String EMPLOYEE_DELETION_FAILED = "EMPLOYEE_DELETION_FAILED";
    
    // Authentication and Authorization error codes
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String INVALID_TOKEN = "INVALID_TOKEN";
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String UNAUTHORIZED_ACCESS = "UNAUTHORIZED_ACCESS";
    public static final String INSUFFICIENT_PERMISSIONS = "INSUFFICIENT_PERMISSIONS";
    
    // Validation error codes
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String INVALID_INPUT = "INVALID_INPUT";
    public static final String MISSING_REQUIRED_FIELD = "MISSING_REQUIRED_FIELD";
    
    // External service error codes
    public static final String EXTERNAL_SERVICE_ERROR = "EXTERNAL_SERVICE_ERROR";
    public static final String RBAC_SERVICE_ERROR = "RBAC_SERVICE_ERROR";
    public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";
    
    // Data integrity error codes
    public static final String DATA_INTEGRITY_VIOLATION = "DATA_INTEGRITY_VIOLATION";
    public static final String DUPLICATE_ENTRY = "DUPLICATE_ENTRY";
    public static final String FOREIGN_KEY_VIOLATION = "FOREIGN_KEY_VIOLATION";
    
    // Generic error codes
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    public static final String OPERATION_FAILED = "OPERATION_FAILED";
} 