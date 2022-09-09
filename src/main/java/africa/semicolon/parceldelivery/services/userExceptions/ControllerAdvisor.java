package africa.semicolon.parceldelivery.services.userExceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final String body = "Oops, that is inaccurate!";

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<Object> handleExistingEmailException(ExistingEmailException existingEmailException, WebRequest request) {
        return handleExceptionInternal(existingEmailException, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public ResponseEntity<Object> handleInvalidUserIdException(InvalidUserIdException userIdException, WebRequest request) {
        return handleExceptionInternal(userIdException, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NonExistingEmailException.class)
    public ResponseEntity<Object> handleNonExistingEmailException(NonExistingEmailException nonExistingEmailException, WebRequest request) {
        return handleExceptionInternal(nonExistingEmailException, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
