package africa.semicolon.parceldelivery.models;

import lombok.Data;

@Data
public class Location {
    private String state;
    private int number;
    private String street;
    private String country;
    private String city;

}
