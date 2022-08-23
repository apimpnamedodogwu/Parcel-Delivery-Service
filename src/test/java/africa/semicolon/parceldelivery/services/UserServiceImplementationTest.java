package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.User;
import africa.semicolon.parceldelivery.repositories.UserRepository;
import africa.semicolon.parceldelivery.requests.UserRegistrationRequest;
import africa.semicolon.parceldelivery.services.userExceptions.InvalidUserIdException;
import africa.semicolon.parceldelivery.services.userExceptions.NonExistingEmailException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImplementation userServiceImplementation;


    @Test
    void testThatUserCanBeGottenByEmail() {
        User user = new User();
        user.setEmail("eden.kwinesta@gmail.com");
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        var existingUser = userServiceImplementation.getUserByEmail(user.getEmail());
        ArgumentCaptor<String> userArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findUserByEmail(userArgumentCaptor.capture());
        var capturedUserArgument = userArgumentCaptor.getValue();
        assertThat(capturedUserArgument).isEqualTo(existingUser.getEmail());
    }

    @Test
    void testThatExceptionMessageIsThrownInMethodGetUserByEmail() {
        User user = new User();
        user.setEmail("eden.kwinesta@gmail.com");
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplementation.getUserByEmail(user.getEmail()))
                .isInstanceOf(NonExistingEmailException.class)
                .hasMessage(user.getEmail() + " does not exist!");
    }

    @Test
    void testThatUserCanBeGottenById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        var existingUser = userServiceImplementation.getUserById(user.getId());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findUserById(argumentCaptor.capture());
        var capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).isEqualTo(existingUser.getId());
    }

    @Test
    void testThatExceptionMessageIsThrownInMethodGetUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplementation.getUserById(user.getId()))
                .isInstanceOf(InvalidUserIdException.class)
                .hasMessage("User with id number " + user.getId() + " does not exist!");
    }
    @Disabled
    @Test
    void testThatUserCanBeCreated() {
        UserRegistrationRequest request = new UserRegistrationRequest();
//        User user = new User();
        request.setEmail("eden.kwinesta@gmail.com");
        request.setFirstName("Eden");
        request.setLastName("Elenwoke");
        request.setUserName("edentheheathen");
        request.setPassword("Eden_247365");
//        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());
        userServiceImplementation.createUser(request);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        var capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getEmail()).isEqualTo(request.getEmail());
        verify(userRepository, times(1)).save(capturedUser);
    }

    @Test
    void testThatAllUsersCanBeGotten() {
        userServiceImplementation.getAllUsers(1);
        verify(userRepository).findAllBy(Pageable.ofSize(20));
    }

    @Test
    void getAllUserParcels() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        userServiceImplementation.getAllUserParcels(user.getId());
        verify(userRepository).findUserById(user.getId());
    }

    @Test
    void testThatExceptionMessageIsThrownInMethodGetAllUserParcels() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplementation.getAllUserParcels(user.getId()))
                .isInstanceOf(InvalidUserIdException.class)
                .hasMessage("User with id number " + user.getId() + " does not exist!");
    }
}