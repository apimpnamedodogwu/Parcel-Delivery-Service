package africa.semicolon.parceldelivery.services.parcelExceptions;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String message) {
        super(message);
    }
}
