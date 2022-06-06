package org.medianik.testmail.repository;

import org.medianik.testmail.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);

    @NotNull
    @Query("select u from users u where u.id = :id")
    @Override
    Optional<User> findById(@NotNull @Param("id") Long id);

    @Modifying
    @Query("update users u set u.balance=u.balance+:money where u.id=:id")
    void addMoneyToUser(@NotNull @Param("id") Long id, BigDecimal money);
}
