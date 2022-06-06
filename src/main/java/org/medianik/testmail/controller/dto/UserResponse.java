package org.medianik.testmail.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.medianik.testmail.model.BookItem;
import org.medianik.testmail.model.User;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse{
    Long id;
    String username;
    BigDecimal money;
    List<BookItem> books;

    public UserResponse(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.money = user.getBalance();
        this.books = user.getBooks();
    }
}
