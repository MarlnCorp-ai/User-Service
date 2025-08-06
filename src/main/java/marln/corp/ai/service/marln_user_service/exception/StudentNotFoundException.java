package marln.corp.ai.service.marln_user_service.exception;

import marln.corp.ai.service.marln_user_service.entity.Student;
import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends BaseException{

    public StudentNotFoundException(String message)
    {
        super(message,
                HttpStatus.NOT_FOUND,
                "USER_NOT_FOUND");

    }
    public StudentNotFoundException(String message, HttpStatus httpStatus, String errorCode) {
        super(message, httpStatus, errorCode);
    }

}
