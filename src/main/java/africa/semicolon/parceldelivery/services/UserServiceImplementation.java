package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.Role;
import africa.semicolon.parceldelivery.models.User;
import africa.semicolon.parceldelivery.repositories.UserRepository;
import africa.semicolon.parceldelivery.requests.UserRegistrationRequest;
import africa.semicolon.parceldelivery.services.userExceptions.ExistingEmailException;
import africa.semicolon.parceldelivery.services.userExceptions.InvalidUserIdException;
import africa.semicolon.parceldelivery.services.userExceptions.NonExistingEmailException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        var user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        throw new NonExistingEmailException(email);
    }

    @Override
    public User getUserById(Long id) {
        var user = userRepository.findUserById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new InvalidUserIdException(id);
    }

    @Override
    public void createUser(UserRegistrationRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        User user = new User();
        var existingUser = userRepository.findUserByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new ExistingEmailException(request.getEmail());
        } else if (UserRegistrationRequest.validateRequestEmail(request.getEmail())
                && UserRegistrationRequest.validateRequestPassword(request.getPassword())
                && UserRegistrationRequest.validateNameFields(request.getFirstName(), request.getLastName(), request.getUserName()))
        {
            modelMapper.map(user, request);
            user.setRole(Role.TYPE_1);
            userRepository.save(user);
        }
    }

    @Override
    public List<User> getAllUsers(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 20);
        return userRepository.findAllBy(pageable);
    }

    @Override
    public List<Parcel> getAllUserParcels(Long userId) {
        var existingUser = userRepository.findUserById(userId);
        if (existingUser.isPresent()) {
            return existingUser.get().getParcels();
        }
        throw new InvalidUserIdException(userId);
    }
}
