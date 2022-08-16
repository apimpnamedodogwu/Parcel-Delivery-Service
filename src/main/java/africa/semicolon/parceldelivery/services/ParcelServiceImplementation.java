package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.ParcelDeliveryStatus;
import africa.semicolon.parceldelivery.repositories.ParcelRepository;
import africa.semicolon.parceldelivery.requests.ParcelCreationRequest;
import africa.semicolon.parceldelivery.services.parcelExceptions.ParcelIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelServiceImplementation implements ParcelService {

    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelServiceImplementation(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public void updateParcelStatus(Long parcelId, String newStatus) {
        var parcel = parcelRepository.findById(parcelId);
        if (parcel.isPresent()) {
            var status = ParcelDeliveryStatus.decode(newStatus);
            parcel.get().setDeliveryStatus(status);
            parcelRepository.save(parcel.get());
            return;
        }
        throw new ParcelIdException(parcelId);
    }

    @Override
    public List<Parcel> getAllParcels(int pageNumber) {
        return null;
    }

    @Override
    public List<Parcel> getAllDeliveredParcels(int pageNumber) {
        return null;
    }

    @Override
    public List<Parcel> getAllParcelsInTransit(int pageNumber) {
        return null;
    }

    @Override
    public List<Parcel> getAllPendingParcels(int pageNumber) {
        return null;
    }

    @Override
    public List<Parcel> getAllFailedParcels(int pageNumber) {
        return null;
    }

    @Override
    public void createParcel(ParcelCreationRequest request) {

    }

    @Override
    public Parcel getParcelDetails(String id) {
        return null;
    }

    @Override
    public void updateParcelLocation(Location newLocation) {

    }
}
