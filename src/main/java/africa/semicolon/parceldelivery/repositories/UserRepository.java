package africa.semicolon.parceldelivery.repositories;

import africa.semicolon.parceldelivery.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
