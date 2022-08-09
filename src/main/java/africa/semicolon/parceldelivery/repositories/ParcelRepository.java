package africa.semicolon.parceldelivery.repositories;

import africa.semicolon.parceldelivery.models.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
}
