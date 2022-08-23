package africa.semicolon.parceldelivery.services;

import africa.semicolon.parceldelivery.models.Parcel;
import africa.semicolon.parceldelivery.models.User;
import africa.semicolon.parceldelivery.requests.UserRegistrationRequest;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Long id);

    void createUser(UserRegistrationRequest request);

    List<User> getAllUsers(int pageNumber);

    List<Parcel> getAllUserParcels(Long userId);
}
