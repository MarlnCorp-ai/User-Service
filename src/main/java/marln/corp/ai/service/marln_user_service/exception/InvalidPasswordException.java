package marln.corp.ai.service.marln_user_service.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException{

    public InvalidPasswordException() {
        super("Invalid current password",
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS");
    }

    public InvalidPasswordException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD");
    }

    public InvalidPasswordException(String message, String errorCode) {
        super(message, HttpStatus.UNAUTHORIZED, errorCode);
    }
}
