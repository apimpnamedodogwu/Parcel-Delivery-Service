package africa.semicolon.parceldelivery.services.parcelExceptions;

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

    @ExceptionHandler(ParcelDeliveryStatusException.class)
    public ResponseEntity<Object> handleParcelDeliveryStatusException(ParcelDeliveryStatusException exception, WebRequest request) {
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ParcelIdException.class)
    public ResponseEntity<Object> handleParcelIdException(ParcelIdException exception, WebRequest request) {
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<Object> handleRoleException(RoleException exception, WebRequest request) {
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<Object> handleEmptyFieldException(EmptyFieldException exception, WebRequest request) {
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
