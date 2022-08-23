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
import org.modelmapper.ModelMapper;
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
        var parcel = parcelRepository.findParcelsById(parcelId);
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
        ModelMapper modelMapper = new ModelMapper();
        request.validateParcelCreation(request);
        var userCreatingAParcelExists = userRepository.findUserByEmail(request.getCreatorEmail());
        if (userCreatingAParcelExists.isPresent()) {
            modelMapper.map(parcel, request);
            parcel.setCreationDate(LocalDateTime.now());
            parcel.setDeliveryStatus(ParcelDeliveryStatus.CODE_1);
            parcel.setDeliveryDate(LocalDateTime.now());
            parcelRepository.save(parcel);
            userCreatingAParcelExists.get().getParcels().add(parcel);
            userRepository.save(userCreatingAParcelExists.get());
            return;
        }
        throw new NonExistingEmailException(request.getCreatorEmail());
    }

    @Override
    public Parcel getParcelDetails(Long id) {
        var existingParcel = parcelRepository.findParcelsById(id);
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
        var existingParcel = parcelRepository.findParcelsById(id);
        if (existingParcel.isPresent()) {
            existingParcel.get().setDeliveryLocation(newLocation);
            parcelRepository.save(existingParcel.get());
            return;
        }
        throw new EmptyFieldException("Cannot save an empty field!");
    }
}
