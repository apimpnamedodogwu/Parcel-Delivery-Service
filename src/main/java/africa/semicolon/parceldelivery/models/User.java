package africa.semicolon.parceldelivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    List<Parcel> parcels = new ArrayList<>();
    private String email;
    private Role role;
    private String password;
    private String firstName;
    private String userName;
    private String lastName;
}
