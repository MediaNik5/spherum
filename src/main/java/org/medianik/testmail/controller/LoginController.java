package org.medianik.testmail.controller;

import lombok.*;
import org.medianik.testmail.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController{
    private final LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody UserRequest body){
        var savedUser = loginService.signup(body.username, body.password);
        return new UserResponse(savedUser.getId(), savedUser.getUsername());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class UserRequest{
        private String username;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class UserResponse{
        private Long id;
        private String username;
    }
}
