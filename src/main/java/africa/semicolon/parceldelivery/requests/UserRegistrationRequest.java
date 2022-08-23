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

    private static String patternForEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
    private static String patternForPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$";

    public static boolean validateRequestEmail(String email) {
        return Pattern.compile(patternForEmail)
                .matcher(email)
                .matches();
    }

    public static boolean validateRequestPassword(String password) {
        return Pattern.compile(patternForPassword)
                .matcher(password)
                .matches();
    }

    public static boolean validateNameFields(String firstName, String lastName, String userName) {
        if (firstName.isEmpty() && lastName.isEmpty() && userName.isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        }
        return true;
    }
}
