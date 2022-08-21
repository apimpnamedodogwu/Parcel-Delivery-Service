package africa.semicolon.parceldelivery.services.userExceptions;

public class NonExistingEmailException extends RuntimeException{
    public NonExistingEmailException(String email) {
        super(String.format(email + " does not exist!"));
    }
}
