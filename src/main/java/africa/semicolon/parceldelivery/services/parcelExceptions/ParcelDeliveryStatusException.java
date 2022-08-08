package africa.semicolon.parceldelivery.services.parcelExceptions;

public class ParcelDeliveryStatusException extends RuntimeException{
    public ParcelDeliveryStatusException (String message) {
        super(message);
    }
}
