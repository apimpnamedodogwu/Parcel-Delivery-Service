package africa.semicolon.parceldelivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private String email;
    private Role role;
    private String password;
    private String firstName;
    private String userName;
    private String lastName;

}
