package africa.semicolon.parceldelivery.controllers;

import africa.semicolon.parceldelivery.requests.UserRegistrationRequest;
import africa.semicolon.parceldelivery.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAUserByEmail(@RequestParam String email) {
        var user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getAUserById(@PathVariable Long userId) {
        var user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationRequest request) {
        userService.createUser(request);
        var displayMessage = "Successful!";
        return new ResponseEntity<>(displayMessage, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(@RequestParam int pageNumber) {
        var list = userService.getAllUsers(pageNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getAUsersParcelList(@PathVariable Long userId) {
        var list = userService.getAllUserParcels(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
