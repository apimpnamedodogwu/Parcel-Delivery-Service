package africa.semicolon.parceldelivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parcel {
    @Id
    private String id;

    private Location deliveryLocation;
    private LocalDateTime deliveryDate;
    private LocalDateTime creationDate;

    private String itemName;
    private String itemDescription;

    @Enumerated(EnumType.STRING)
    private ParcelDeliveryStatus deliveryStatus;
}
