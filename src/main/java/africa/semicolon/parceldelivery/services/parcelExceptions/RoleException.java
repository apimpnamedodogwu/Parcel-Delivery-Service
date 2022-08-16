package africa.semicolon.parceldelivery.services.parcelExceptions;

public class RoleException extends RuntimeException {
    public RoleException(String role) {
        super(String.format(role + " is invalid!"));
    }
}
