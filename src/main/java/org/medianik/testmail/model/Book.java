package org.medianik.testmail.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "books")
@Entity(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private BigDecimal price;

    public Book(String name, String author, BigDecimal price){
        this.name = name;
        this.author = author;
        this.price = price;
    }
}
