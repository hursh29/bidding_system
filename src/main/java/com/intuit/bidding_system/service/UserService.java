package com.intuit.bidding_system.service;

import com.intuit.bidding_system.entity.User;
import com.intuit.bidding_system.repository.UserRepository;
import com.intuit.bidding_system.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.intuit.bidding_system.entity.UserRole.VENDOR;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public boolean validateVendorExistence(final Long vendorId) {
        return userRepository.findByUserId(vendorId)
            .filter(mayBeUser -> VENDOR.equals(mayBeUser.getRole()))
            .isPresent();
    }
}
