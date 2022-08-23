package africa.semicolon.parceldelivery.services.userExceptions;

public class InvalidUserIdException extends RuntimeException{
    public InvalidUserIdException(Long id) {
        super(String.format("User with id number " + id + " does not exist!"));
    }
}
