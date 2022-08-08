package africa.semicolon.parceldelivery.requests;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
