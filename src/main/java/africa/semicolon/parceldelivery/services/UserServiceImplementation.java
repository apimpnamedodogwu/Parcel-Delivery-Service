package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.User;
import africa.semicolon.parceldelivery.repositories.UserRepository;
import africa.semicolon.parceldelivery.requests.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public void createUser(UserRegistrationRequest request) {

    }

    @Override
    public List<User> getAllUsers(int pageNumber) {
        return null;
    }

    @Override
    public List<User> getAllUserParcels(String userId) {
        return null;
    }
}
