package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.ParcelDeliveryStatus;
import africa.semicolon.parceldelivery.repositories.ParcelRepository;
import africa.semicolon.parceldelivery.repositories.UserRepository;
import africa.semicolon.parceldelivery.requests.ParcelCreationRequest;
import africa.semicolon.parceldelivery.services.parcelExceptions.EmptyFieldException;
import africa.semicolon.parceldelivery.services.parcelExceptions.ParcelIdException;
import africa.semicolon.parceldelivery.services.userExceptions.NonExistingEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParcelServiceImplementation implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final UserRepository userRepository;

    @Autowired
    public ParcelServiceImplementation(ParcelRepository parcelRepository, UserRepository userRepository) {
        this.parcelRepository = parcelRepository;
        this.userRepository = userRepository;
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
        Pageable pageable = PageRequest.of(pageNumber - 1, 20);
        return parcelRepository.findAllBy(pageable);
    }

    @Override
    public List<Parcel> getAllDeliveredParcels(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 20);
        return parcelRepository.findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_3, pageable);
    }

    @Override
    public List<Parcel> getAllParcelsInTransit(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 20);
        return parcelRepository.findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_2, pageable);
    }

    @Override
    public List<Parcel> getAllPendingParcels(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 20);
        return parcelRepository.findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_1, pageable);
    }

    @Override
    public List<Parcel> getAllFailedParcels(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 20);
        return parcelRepository.findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_0, pageable);
    }

    @Override
    public void createParcel(ParcelCreationRequest request) {
        Parcel parcel = new Parcel();
        request.validateParcelCreation(request);
        var userCreatingAParcelExists = userRepository.findUserByEmail(request.getCreatorEmail());
        if (userCreatingAParcelExists.isPresent()) {
            parcel.setItemName(request.getItemName());
            parcel.setItemDescription(request.getItemDescription());
            parcel.setCreationDate(LocalDateTime.now());
            parcel.setDeliveryStatus(ParcelDeliveryStatus.CODE_1);
            parcel.setDeliveryDate(LocalDateTime.now());
            parcel.setDeliveryLocation(request.getDeliveryLocation());
            parcelRepository.save(parcel);
            return;
        }
        throw new NonExistingEmailException(request.getCreatorEmail());
    }

    @Override
    public Parcel getParcelDetails(Long id) {
        var existingParcel = parcelRepository.findById(id);
        if (existingParcel.isPresent()) {
            return existingParcel.get();
        }
        throw new ParcelIdException(id);
    }

    @Override
    public void updateParcelLocation(Long id, Location newLocation) {
        if (newLocation == null) {
            throw new EmptyFieldException("Cannot save an empty field!");
        }
        var existingParcel = parcelRepository.findById(id);
       if (existingParcel.isPresent()) {
           existingParcel.get().setDeliveryLocation(newLocation);
           parcelRepository.save(existingParcel.get());
           return;
       } throw new EmptyFieldException("Cannot save an empty field!");
    }
}
