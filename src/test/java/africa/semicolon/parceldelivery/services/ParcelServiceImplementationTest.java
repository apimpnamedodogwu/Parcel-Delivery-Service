package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.ParcelDeliveryStatus;
import africa.semicolon.parceldelivery.models.User;
import africa.semicolon.parceldelivery.repositories.ParcelRepository;
import africa.semicolon.parceldelivery.repositories.UserRepository;
import africa.semicolon.parceldelivery.requests.ParcelCreationRequest;
import africa.semicolon.parceldelivery.services.parcelExceptions.EmptyFieldException;
import africa.semicolon.parceldelivery.services.parcelExceptions.ParcelDeliveryStatusException;
import africa.semicolon.parceldelivery.services.parcelExceptions.ParcelIdException;
import africa.semicolon.parceldelivery.services.userExceptions.NonExistingEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Pageable;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParcelServiceImplementationTest {
    @Mock
    ParcelRepository parcelRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ParcelServiceImplementation parcelServiceImplementation;


    @Test
    void testThatParcelStatusCanBeUpdated() {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        String status = "PENDING";
        when(parcelRepository.findParcelsById(parcel.getId())).thenReturn(Optional.of(parcel));
        parcelServiceImplementation.updateParcelStatus(parcel.getId(), status);
        ArgumentCaptor<Parcel> parcelArgumentCaptor = ArgumentCaptor.forClass(Parcel.class);
        verify(parcelRepository).save(parcelArgumentCaptor.capture());
        var newStatus = parcelArgumentCaptor.getValue();
        assertThat(newStatus.getDeliveryStatus()).isEqualTo(ParcelDeliveryStatus.CODE_1);
        verify(parcelRepository, times(1)).save(parcel);
    }

    @Test
    void testThatParcelStatusExceptionInMethodUpdateParcelIsThrown() {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        String status = "Bread cake";
        when(parcelRepository.findParcelsById(parcel.getId())).thenReturn(Optional.of(parcel));
        assertThatThrownBy(() -> parcelServiceImplementation.updateParcelStatus(parcel.getId(), status))
                .isInstanceOf(ParcelDeliveryStatusException.class)
                .hasMessage("This " + status + " is invalid!");
    }

    @Test
    void testThatParcelIdExceptionInMethodUpdateParcelIsThrown() {
        Parcel parcel = new Parcel();
        String status = "DELIVERED";
        parcel.setId(1L);
        when(parcelRepository.findParcelsById(parcel.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> parcelServiceImplementation.updateParcelStatus(parcel.getId(), status))
                .isInstanceOf(ParcelIdException.class)
                .hasMessage("Parcel with id %d does not exist!", parcel.getId());
    }

    @Test
    void testThatAllParcelsCanBeGotten() {
        parcelServiceImplementation.getAllParcels(1);
        verify(parcelRepository).findAllBy(Pageable.ofSize(20));
    }

    @Test
    void testThatAllDeliveredParcelsCanBeGotten() {
        parcelServiceImplementation.getAllDeliveredParcels(1);
        verify(parcelRepository).findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_3, Pageable.ofSize(20));
    }

    @Test
    void testThatAllParcelsInTransitCanBeGotten() {
        parcelServiceImplementation.getAllParcelsInTransit(1);
        verify(parcelRepository).findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_2, Pageable.ofSize(20));
    }

    @Test
    void testThatAllPendingParcelsCanBeGotten() {
        parcelServiceImplementation.getAllPendingParcels(1);
        verify(parcelRepository).findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_1, Pageable.ofSize(20));
    }

    @Test
    void testThatAllFailedParcelsCanBeGotten() {
        parcelServiceImplementation.getAllFailedParcels(1);
        verify(parcelRepository).findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_0, Pageable.ofSize(20));
    }

    @Test
    void testThatAParcelCanBeCreated() {
        ParcelCreationRequest request = new ParcelCreationRequest();
        User user = new User();
        Location location = new Location();
        request.setCreatorEmail("edenelenwoke@gmail.com");
        request.setItemDescription("strictly for pleasure");
        request.setItemName("douche bag");
        location.setCity("Lagos");
        location.setCountry("Nigeria");
        location.setState("Lagos");
        location.setStreet("Emily Akinola");
        location.setNumber(30);
        request.setDeliveryLocation(location);
        when(userRepository.findUserByEmail(request.getCreatorEmail())).thenReturn(Optional.of(user));
        parcelServiceImplementation.createParcel(request);
        ArgumentCaptor<Parcel> argumentCaptor = ArgumentCaptor.forClass(Parcel.class);
        verify(parcelRepository).save(argumentCaptor.capture());
        var capturedNewParcel = argumentCaptor.getValue();
        assertThat(capturedNewParcel.getItemName()).isEqualTo(request.getItemName());
        assertThat(capturedNewParcel.getItemDescription()).isEqualTo(request.getItemDescription());
        assertThat(capturedNewParcel.getDeliveryLocation()).isEqualTo(request.getDeliveryLocation());
        verify(parcelRepository, times(1)).save(capturedNewParcel);
        verify(userRepository, times(1)).findUserByEmail(request.getCreatorEmail());
    }

    @Test
    void testThatEmptyFieldExceptionInMethodCreateParcelIsThrown() {
        ParcelCreationRequest request = new ParcelCreationRequest();
        request.setCreatorEmail("");
        request.setItemName("");
        request.setDeliveryLocation(null);
        assertThatThrownBy(() -> parcelServiceImplementation.createParcel(request))
                .isInstanceOf(EmptyFieldException.class)
                .hasMessage("No field can be empty!");
    }

    @Test
    void testThatNonExistingEmailExceptionInMethodCreateParcelIsThrown() {
        ParcelCreationRequest request = new ParcelCreationRequest();
        request.setCreatorEmail("edenelenwoke@gmail.com");
        request.setItemName("record player");
        when(userRepository.findUserByEmail(request.getCreatorEmail())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> parcelServiceImplementation.createParcel(request))
                .isInstanceOf(NonExistingEmailException.class)
                .hasMessage(request.getCreatorEmail() + " does not exist!");
    }

    @Test
    void testThatParcelDetailsCanBeGotten() {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        parcel.setItemName("bread");
        when(parcelRepository.findParcelsById(parcel.getId())).thenReturn(Optional.of(parcel));
        parcelServiceImplementation.getParcelDetails(parcel.getId());
        ArgumentCaptor<Long> parcelArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(parcelRepository).findParcelsById(parcelArgumentCaptor.capture());
        var capturedParcels = parcelArgumentCaptor.getValue();
        assertThat(capturedParcels).isEqualTo(parcel.getId());
    }

    @Test
    void testThatParcelIdExceptionIsThrownInMethodGetParcelDetails() {
        Parcel parcel = new Parcel();
        parcel.setId(null);
        when(parcelRepository.findParcelsById(parcel.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> parcelServiceImplementation.getParcelDetails(parcel.getId()))
                .isInstanceOf(ParcelIdException.class)
                .hasMessage("Parcel with id %d does not exist!", parcel.getId());
    }

    @Test
    void testThatParcelLocationCanBeUpdated() {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        Location location = new Location();
        location.setCity("Lagos");
        location.setCountry("Nigeria");
        location.setState("Lagos");
        location.setStreet("Emily Akinola");
        location.setNumber(30);
        when(parcelRepository.findParcelsById(parcel.getId())).thenReturn(Optional.of(parcel));
        parcelServiceImplementation.updateParcelLocation(parcel.getId(), location);
        ArgumentCaptor<Parcel> argumentCaptor = ArgumentCaptor.forClass(Parcel.class);
        verify(parcelRepository).save(argumentCaptor.capture());
        var captured = argumentCaptor.getValue();
        assertThat(captured.getDeliveryLocation()).isEqualTo(location);
        verify(parcelRepository, times(1)).save(captured);
    }

    @Test
    void testThatEmptyFieldExceptionIsThrownInMethodUpdateParcelLocation() {
        Location location = new Location();
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        location.setNumber(0);
        location.setState("");
        location.setStreet("");
        location.setCity("");
        location.setCountry("");
        when(parcelRepository.findParcelsById(parcel.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> parcelServiceImplementation.updateParcelLocation(parcel.getId(), location))
                .isInstanceOf(EmptyFieldException.class)
                .hasMessage("Cannot save an empty field!");
    }
}
