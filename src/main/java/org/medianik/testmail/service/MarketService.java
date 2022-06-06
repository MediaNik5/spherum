package org.medianik.testmail.service;

import org.medianik.testmail.controller.dto.BookItemRequest;
import org.medianik.testmail.controller.dto.ProductsResponse;
import org.medianik.testmail.exception.RequestException;
import org.medianik.testmail.model.BookItem;
import org.medianik.testmail.repository.BookItemRepository;
import org.medianik.testmail.repository.MarketRepository;
import org.medianik.testmail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Service
public class MarketService{
    private final BookItemRepository bookItemRepository;
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;

    public MarketService(
        @Autowired BookItemRepository bookItemRepository,
        @Autowired MarketRepository marketRepository,
        @Autowired UserRepository userRepository
    ){
        this.bookItemRepository = bookItemRepository;
        this.marketRepository = marketRepository;
        this.userRepository = userRepository;
    }

    @NotNull
    public ProductsResponse getBooks(){
        var market = marketRepository.findById(1L)
            .orElseThrow(() -> new RequestException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "market.not_initialized"));

        return new ProductsResponse(market.getBooks());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addBookToMarket(BookItemRequest body){
        var bookItem = bookItemRepository.findBookItemByBook_Id(body.getBookId())
            .orElseGet(() -> new BookItem(null, 0L));
        bookItem.setAmount(bookItem.getAmount() + body.getAmount());

        bookItemRepository.save(bookItem);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void buyBook(Long userId, Long bookItemId, Long amount){
        var bookItem = bookItemRepository.findById(bookItemId)
            .orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND.value(), "book_item.not_found"));

        if(bookItem.getAmount() < amount){
            throw new RequestException(HttpStatus.UNPROCESSABLE_ENTITY.value(), "book_item.not_enough");
        }
        // UserDetails could have been gotten out of our transaction
        var user = userRepository.findById(userId).get();

        BigDecimal userMoney = user.getBalance();
        BigDecimal singleBookPrice = bookItem.getBook().getPrice();
        BigDecimal sum = singleBookPrice.multiply(BigDecimal.valueOf(amount));

        if(userMoney.compareTo(sum) < 0){
            throw new RequestException(HttpStatus.UNPROCESSABLE_ENTITY.value(), "user.money.not_enough");
        }
        user.setBalance(userMoney.subtract(sum));
        bookItem.setAmount(bookItem.getAmount() - amount);
        bookItemRepository.save(bookItem);

        for(BookItem existingBookItem : user.getBooks()){
            var existingId = existingBookItem.getBook().getId();
            if(existingId.equals(bookItem.getBook().getId())){
                existingBookItem.setAmount(existingBookItem.getAmount() + amount);
                userRepository.save(user);
                return;
            }
        }

        user.getBooks().add(new BookItem(bookItem.getBook(), amount));
        userRepository.save(user);
    }
}

