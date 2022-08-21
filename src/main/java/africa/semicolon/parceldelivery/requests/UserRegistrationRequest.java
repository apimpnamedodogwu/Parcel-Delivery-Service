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

    private final String patternForEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
    private final String patternForPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$";

    public boolean validateRequestEmail(UserRegistrationRequest request) {
        return Pattern.compile(patternForEmail)
                .matcher(request.getEmail())
                .matches();
    }

    public boolean validateRequestPassword(UserRegistrationRequest request) {
        return Pattern.compile(patternForPassword)
                .matcher(request.getPassword())
                .matches();
    }

    public UserRegistrationRequest validateNameFields(UserRegistrationRequest name) {
        if (name.firstName.isEmpty() && name.lastName.isEmpty() && name.userName.isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        }
        return name;
    }
}
