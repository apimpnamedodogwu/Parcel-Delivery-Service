package africa.semicolon.parceldelivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parcel")
public class Parcel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Location deliveryLocation;
    private LocalDateTime deliveryDate;
    private LocalDateTime creationDate;

    private String itemName;
    private String itemDescription;
    private ParcelDeliveryStatus deliveryStatus;

}

