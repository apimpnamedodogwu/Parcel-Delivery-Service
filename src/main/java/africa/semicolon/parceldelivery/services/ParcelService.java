package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.requests.ParcelCreationRequest;

import java.util.List;

public interface ParcelService {

    void updateParcelStatus(long parcelId, String newStatus);

    List<Parcel> getAllParcels(int pageNumber);

    List<Parcel> getAllDeliveredParcels(int pageNumber);

    List<Parcel> getAllParcelsInTransit(int pageNumber);

    List<Parcel> getAllPendingParcels(int pageNumber);

    List<Parcel> getAllFailedParcels(int pageNumber);

    void createParcel(ParcelCreationRequest request);

    Parcel getParcelDetails(String id);

    void updateParcelLocation(Location newLocation);

}
