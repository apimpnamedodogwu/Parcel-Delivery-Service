package africa.semicolon.parceldelivery.repositories;

import africa.semicolon.parceldelivery.models.Location;
import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.ParcelDeliveryStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ParcelRepositoryTest {

    @Autowired
    ParcelRepository parcelRepository;

    @Test
    void testThatInjectedComponentsAreNotNull() {
        assertThat(parcelRepository).isNotNull();
    }

    @AfterEach
    void tearDown() {
        parcelRepository.deleteAll();
    }

    @Test
    void testThatParcelCanBeFoundById() {
        Parcel parcel = new Parcel();
        Location location = new Location();
        location.setCity("Lagos");
        location.setCountry("Nigeria");
        location.setState("Lagos");
        location.setStreet("Emily Akinola");
        location.setNumber(30);
        parcel.setItemName("hand cuffs");
        parcel.setItemDescription("feisty");
        parcel.setDeliveryLocation(location);
        parcel.setId(1L);
        parcel.setDeliveryDate(LocalDateTime.now());
        parcel.setCreationDate(LocalDateTime.now());
        parcel.setDeliveryStatus(ParcelDeliveryStatus.CODE_1);
        var savedParcel = parcelRepository.save(parcel);
        var existingParcel = parcelRepository.findParcelsById(savedParcel.getId());
        assertThat(existingParcel.isPresent()).isTrue();
    }

    @Disabled
    @Test
    void findAllBy() {
    }

    @Disabled
    @Test
    void findParcelsByDeliveryStatus() {
    }
}