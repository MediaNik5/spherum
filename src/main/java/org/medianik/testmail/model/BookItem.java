package org.medianik.testmail.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "book_item")
@Entity(name = "book_item")
@Getter
@Setter
@NoArgsConstructor
public class BookItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Book book;
    @Column(nullable = false)
    private Long amount;

    public BookItem(Book book, Long amount){
        this.book = book;
        this.amount = amount;
    }
}
