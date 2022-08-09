package africa.semicolon.parceldelivery.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private String state;
    private int number;
    private String street;
    private String country;
    private String city;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
