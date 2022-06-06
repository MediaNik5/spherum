package org.medianik.testmail.service;

import org.medianik.testmail.controller.dto.UserResponse;
import org.medianik.testmail.exception.RequestException;
import org.medianik.testmail.model.User;
import org.medianik.testmail.repository.UserRepository;
import org.medianik.testmail.security.PostgresUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addMoneyToUser(PostgresUserDetails userDetails, BigDecimal money){
        if(money.compareTo(BigDecimal.ZERO) <= 0)
            throw new RequestException(400, "money.zero-or-negative");
        userRepository.addMoneyToUser(userDetails.getUser().getId(), money);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RequestException(404, "user.not_found"));
    }
}
