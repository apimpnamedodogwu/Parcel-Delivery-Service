package africa.semicolon.parceldelivery.requests;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.services.parcelExceptions.EmptyFieldException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelCreationRequest {

    private String creatorEmail;
    private String itemName;
    private String itemDescription;
    private Location deliveryLocation;

    public void validateParcelCreation(ParcelCreationRequest request) {
        if (request.creatorEmail.isEmpty() && request.getItemName().isEmpty() && request.getDeliveryLocation() == null) {
            throw new EmptyFieldException("No field can be empty!");
        }
    }

}
