package africa.semicolon.parceldelivery.repositories;

import africa.semicolon.parceldelivery.models.Role;
import africa.semicolon.parceldelivery.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    User user;
    User userTwo;

    @BeforeEach
    void setUp() {
        user = new User();
        userTwo = new User();
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
        var existingUser = userRepository.findUserByEmail(savedUser.getEmail());
        assertThat(existingUser.isPresent()).isTrue();
        assertThat(existingUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    void testThatUserCanBeFoundById() {
        user.setFirstName("Eden");
        user.setLastName("Elenwoke");
        user.setPassword("12345678");
        user.setUserName("edentheheathen");
        user.setEmail("eden.kwinesta@gamil.com");
        user.setRole(Role.TYPE_1);
        var savedUser = userRepository.save(user);
        var existingUser = userRepository.findUserById(savedUser.getId());
        assertThat(existingUser.isPresent()).isTrue();
        assertThat(existingUser.get().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    void testThatAllUsersCanBeFound() {
        user.setFirstName("Eden");
        user.setLastName("Elenwoke");
        user.setPassword("12345678");
        user.setUserName("edentheheathen");
        user.setEmail("eden.kwinesta@gamil.com");
        user.setRole(Role.TYPE_1);
        userRepository.save(user);
        userTwo.setFirstName("Dorcas");
        userTwo.setLastName("Abang");
        userTwo.setUserName("dollydee");
        userTwo.setPassword("12345678");
        userTwo.setEmail("adeweh@gmail.com");
        userTwo.setRole(Role.TYPE_1);
        userRepository.save(userTwo);
        var list = userRepository.findAllBy(Pageable.ofSize(20));
        assertThat(list.size()).isEqualTo(2);
    }
}