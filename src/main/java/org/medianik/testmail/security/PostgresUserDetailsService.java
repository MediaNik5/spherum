package org.medianik.testmail.security;

import org.medianik.testmail.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

public class PostgresUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    public PostgresUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        return new PostgresUserDetails(
            List.of(new SimpleGrantedAuthority("USER")),
            user
        );
    }
}
