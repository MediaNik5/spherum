package org.medianik.testmail.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String hashedPassword;

    public User(String username, String hashedPassword){
        this.username = username;
        this.hashedPassword = hashedPassword;
        balance = BigDecimal.ZERO;
    }

    @Column(precision = 12, scale = 2) // two digits after decimal point
    private BigDecimal balance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BookItem> books;

}
