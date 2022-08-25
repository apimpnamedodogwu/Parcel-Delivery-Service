package africa.semicolon.parceldelivery.repositories;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.ParcelDeliveryStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ParcelRepositoryTest {

    @Autowired
    ParcelRepository parcelRepository;
    Parcel parcel;
    Location location;

    @Test
    void testThatInjectedComponentsAreNotNull() {
        assertThat(parcelRepository).isNotNull();
    }

    @BeforeEach
    void setUp() {
        parcel = new Parcel();
        location = new Location();
    }

    @Test
    void testThatParcelCanBeFoundById() {
        location.setCity("Lagos");
        location.setCountry("Nigeria");
        location.setState("Lagos");
        location.setStreet("Emily Akinola");
        location.setNumber(30);
        parcel.setItemName("hand cuffs");
        parcel.setItemDescription("feisty");
        parcel.setDeliveryLocation(location);
        parcel.setDeliveryDate(LocalDateTime.now());
        parcel.setCreationDate(LocalDateTime.now());
        parcel.setDeliveryStatus(ParcelDeliveryStatus.CODE_1);
        var savedParcel = parcelRepository.save(parcel);
        var existingParcel = parcelRepository.findParcelsById(savedParcel.getId());
        assertThat(existingParcel.isPresent()).isTrue();
    }

    @Test
    void testThatParcelsCanBeFoundByPageable() {
        location.setCity("Lagos");
        location.setCountry("Nigeria");
        location.setState("Lagos");
        location.setStreet("Emily Akinola");
        location.setNumber(30);
        parcel.setItemName("hand cuffs");
        parcel.setItemDescription("feisty");
        parcel.setDeliveryLocation(location);
        parcel.setDeliveryDate(LocalDateTime.now());
        parcel.setCreationDate(LocalDateTime.now());
        parcel.setDeliveryStatus(ParcelDeliveryStatus.CODE_1);
        parcelRepository.save(parcel);
        var list = parcelRepository.findAllBy(Pageable.ofSize(20));
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void findParcelsByDeliveryStatus() {
        location.setCity("Lagos");
        location.setCountry("Nigeria");
        location.setState("Lagos");
        location.setStreet("Emily Akinola");
        location.setNumber(30);
        parcel.setItemName("hand cuffs");
        parcel.setItemDescription("feisty");
        parcel.setDeliveryLocation(location);
        parcel.setDeliveryDate(LocalDateTime.now());
        parcel.setCreationDate(LocalDateTime.now());
        parcel.setDeliveryStatus(ParcelDeliveryStatus.CODE_1);
        parcelRepository.save(parcel);
        var list = parcelRepository.findParcelsByDeliveryStatus(ParcelDeliveryStatus.CODE_1, Pageable.ofSize(20));
        assertThat(list.size()).isEqualTo(1);
    }
}