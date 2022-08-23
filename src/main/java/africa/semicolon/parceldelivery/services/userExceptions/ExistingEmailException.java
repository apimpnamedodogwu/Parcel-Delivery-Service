package africa.semicolon.parceldelivery.services.userExceptions;

public class ExistingEmailException extends RuntimeException {
    public ExistingEmailException(String email) {
        super(String.format("User with email address " + email + " already exists!"));
    }
}
