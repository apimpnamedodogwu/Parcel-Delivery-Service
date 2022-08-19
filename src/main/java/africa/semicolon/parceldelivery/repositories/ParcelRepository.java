package africa.semicolon.parceldelivery.repositories;

import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.ParcelDeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
   Optional<Parcel> findById(long id);
   List<Parcel> findAllBy(Pageable pageable);
   List<Parcel> findParcelsByDeliveryStatus(ParcelDeliveryStatus status, Pageable pageable);

}
