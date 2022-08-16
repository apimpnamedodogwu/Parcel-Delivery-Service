package africa.semicolon.parceldelivery.services.parcelExceptions;

public class ParcelDeliveryStatusException extends RuntimeException{
    public ParcelDeliveryStatusException (String status) {
        super(String.format("This " + status + " is invalid!"));
    }
}
