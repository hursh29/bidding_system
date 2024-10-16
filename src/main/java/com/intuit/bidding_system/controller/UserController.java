package com.intuit.bidding_system.controller;

import com.intuit.bidding_system.entity.User;
import com.intuit.bidding_system.entity.UserRole;
import com.intuit.bidding_system.repository.UserRepository;
import com.intuit.bidding_system.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Data
class UserPayload {

    private Long userId;

    private String username;

    private String email;

    private String phoneNumber;

    private UserRole role;

    private String address;
}

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/registerUser")
    public ResponseEntity<User> persistUser(final @RequestBody UserPayload requestPayload) {
        final var currentTime = LocalDateTime.now();

        final var newUser = User.builder()
            .buildWithAddress(requestPayload.getAddress())
            .buildWithCreatedAt(currentTime)
            .buildWithEmail(requestPayload.getEmail())
            .buildWithPhoneNumber(requestPayload.getPhoneNumber())
            .buildWithUpdatedAt(currentTime)
            .buildWithRole(requestPayload.getRole())
            .buildWithUsername(requestPayload.getUsername())
            .build();

        final var persistedUser = userService.saveUser(newUser);

        return ResponseEntity.ok().body(persistedUser);
    }

    @GetMapping(path = "/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
