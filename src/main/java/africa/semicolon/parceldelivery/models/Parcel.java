package africa.semicolon.parceldelivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parcel")
public class Parcel {
    @javax.persistence.Id
    @Id
    @SequenceGenerator(
            name = "parcel_sequence",
            sequenceName = "parcel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "parcel_sequence")
    private Long id;

    @Embedded
    private Location deliveryLocation;
    private LocalDateTime deliveryDate;
    private LocalDateTime creationDate;

    @NotEmpty(message = "name cannot be empty!")
    private String itemName;

    private String itemDescription;

    @Enumerated(EnumType.ORDINAL)
    private ParcelDeliveryStatus deliveryStatus;

}

