package africa.semicolon.parceldelivery.repositories;

import africa.semicolon.parceldelivery.models.Role;
import africa.semicolon.parceldelivery.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testThatUserCanBeFoundByEmail() {
        user.setFirstName("Eden");
        user.setLastName("Elenwoke");
        user.setPassword("12345678");
        user.setUserName("edentheheathen");
        user.setEmail("eden.kwinesta@gamil.com");
        user.setRole(Role.TYPE_1);
        var savedUser = userRepository.save(user);
        var email = userRepository.findUserByEmail(savedUser.getEmail());
        assertThat(email.isPresent()).isTrue();
        assertThat(email.get().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    void findUserById() {
    }

    @Test
    void findAllBy() {
    }
}