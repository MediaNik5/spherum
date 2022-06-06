package org.medianik.testmail.service;

import org.medianik.testmail.exception.RequestException;
import org.medianik.testmail.model.User;
import org.medianik.testmail.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class LoginService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User signup(String username, String password){
        var user = userRepository.findByUsername(username);
        if(user.isPresent()){
            throw new RequestException(HttpStatus.UNPROCESSABLE_ENTITY.value(), "user.already_exists");
        }

        return userRepository.save(new User(username, passwordEncoder.encode(password)));
    }
}
