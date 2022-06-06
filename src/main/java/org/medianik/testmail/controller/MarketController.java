package org.medianik.testmail.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.medianik.testmail.controller.dto.BookItemRequest;
import org.medianik.testmail.controller.dto.BuyBookItemRequest;
import org.medianik.testmail.controller.dto.ProductsResponse;
import org.medianik.testmail.security.PostgresUserDetails;
import org.medianik.testmail.security.annotation.CurrentUser;
import org.medianik.testmail.service.MarketService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
@SecurityRequirement(name = "basicAuth")
public class MarketController{
    private final MarketService marketService;


    public MarketController(MarketService marketService){
        this.marketService = marketService;
    }

    @GetMapping("/")
    @ApiResponse(responseCode = "500", description = "market.not_initialized. Market has not yet been initialized.")
    public ProductsResponse getBooks(){
        return marketService.getBooks();
    }

    @PostMapping("/add_book")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addBookToMarket(@RequestBody BookItemRequest body){
        marketService.addBookToMarket(body);
    }

    @ApiResponse(responseCode = "404", description = "book_item.not_found. Book was not found.")
    @ApiResponse(responseCode = "422", description = "book_item.not_enough. There is not enough stock in market to buy this amount of books.")
    @ApiResponse(responseCode = "422", description = "user.money.not_enough. User doesn't have enough money.")
    @PostMapping("/deal")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void buyBook(
        @CurrentUser PostgresUserDetails userDetails,
        @RequestBody BuyBookItemRequest body
    ){
        marketService.buyBook(userDetails.getUser().getId(), body.getBookItemId(), body.getAmount());
    }

}
