package africa.semicolon.parceldelivery;

import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.ParcelDeliveryStatus;
import africa.semicolon.parceldelivery.repositories.ParcelRepository;
import africa.semicolon.parceldelivery.services.ParcelServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Pageable;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParcelServiceImplementationTest {
    @Mock
    ParcelRepository parcelRepository;
    @InjectMocks
    ParcelServiceImplementation parcelServiceImplementation;



    @Test
    void testThatParcelStatusCanBeUpdated() {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        String status = "PENDING";
        when(parcelRepository.findById(parcel.getId())).thenReturn(Optional.of(parcel));
        parcelServiceImplementation.updateParcelStatus(parcel.getId(), status);
        ArgumentCaptor<Parcel> parcelArgumentCaptor = ArgumentCaptor.forClass(Parcel.class);
        verify(parcelRepository).save(parcelArgumentCaptor.capture());
        var newStatus = parcelArgumentCaptor.getValue();
        assertThat(newStatus.getDeliveryStatus()).isEqualTo(ParcelDeliveryStatus.CODE_1);
        verify(parcelRepository, times(1)).save(parcel);
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
}
