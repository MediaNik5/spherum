package org.medianik.testmail.security;

import org.medianik.testmail.model.Book;
import org.medianik.testmail.model.BookItem;
import org.medianik.testmail.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class PostgresUserDetails implements UserDetails{
    private final Collection<? extends GrantedAuthority> authorities;
    private final User user;

    public PostgresUserDetails(
        Collection<? extends GrantedAuthority> authorities,
        User user
    ){
        this.authorities = authorities;
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword(){
        return user.getHashedPassword();
    }

    @Override
    public String getUsername(){
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
