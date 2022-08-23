package africa.semicolon.parceldelivery.requests;

import africa.semicolon.parceldelivery.models.User;
import africa.semicolon.parceldelivery.services.parcelExceptions.EmptyFieldException;
import lombok.Data;

import java.util.regex.Pattern;

@Data
public class UserRegistrationRequest {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private static String patternForEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static boolean validateRequestEmail(String email) {
        return Pattern.compile(patternForEmail)
                .matcher(email)
                .matches();
    }

    public static boolean validateRequestPassword(String password) {
        return password.length() == 8;
    }

    public static boolean validateNameFields(String firstName, String lastName, String userName) {
        if (firstName.isEmpty() && lastName.isEmpty() && userName.isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        }
        return true;
    }
}
