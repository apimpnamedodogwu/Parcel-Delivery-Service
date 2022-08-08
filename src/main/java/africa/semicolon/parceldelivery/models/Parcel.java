package africa.semicolon.parceldelivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parcel {
    private Location deliveryLocation;
    private LocalDateTime deliveryDate;
    private LocalDateTime creationDate;
    @Id
    private String id;
    private String itemName;
    private String itemDescription;
    private ParcelDeliveryStatus deliveryStatus;
}
