package africa.semicolon.parceldelivery.requests;

import africa.semicolon.parceldelivery.models.Location;

public class ParcelCreationRequest {
    private String creatorEmail;
    private String itemName;
    private String itemDescription;
    private Location deliveryLocation;
}
