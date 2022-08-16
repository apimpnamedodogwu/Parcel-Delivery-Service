package africa.semicolon.parceldelivery.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {

    @javax.persistence.Id
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Parcel> parcels = new ArrayList<>();

    @NotEmpty(message = "email must not be empty!")
    private String email;

    private Role role;

    @NotEmpty(message = "password cannot be empty!")
    private String password;

    @NotEmpty(message = "first name field cannot be empty!")
    private String firstName;

    @NotEmpty(message = "username cannot be empty!")
    private String userName;

    @NotEmpty(message = "last name field cannot be empty!")
    private String lastName;

}
