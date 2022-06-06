package org.medianik.testmail.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.medianik.testmail.controller.dto.UserResponse;
import org.medianik.testmail.security.PostgresUserDetails;
import org.medianik.testmail.security.annotation.CurrentUser;
import org.medianik.testmail.service.UserService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping()
@SecurityRequirement(name = "bearerAuth")
public class UserController{

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/account")
    public UserResponse getUser(@CurrentUser PostgresUserDetails userDetails){
        return new UserResponse(userService.getUser(userDetails.getUser().getId()));
    }

    @ApiResponse(responseCode = "400", description = "money.zero-or-negative. Money value must be positive.")
    @PostMapping("/add_money")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addMoney(
        @CurrentUser PostgresUserDetails userDetails,
        @RequestParam BigDecimal money
    ){
        userService.addMoneyToUser(userDetails, money);
    }

}
