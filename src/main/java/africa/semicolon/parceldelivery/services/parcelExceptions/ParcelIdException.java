package africa.semicolon.parceldelivery.services.parcelExceptions;

public class ParcelIdException extends RuntimeException {
    public ParcelIdException(Long id) {
        super(String.format("Parcel with id %d does not exist!", id));
    }
}
