package org.medianik.testmail.security;

import org.medianik.testmail.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .cors()
                .and()
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers(
                    "/",
                    "/swagger-ui/index.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/login",
                    "/signup").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic()
                .and();
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//                .permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        return new PostgresUserDetailsService(userRepository);
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Bean
    public PasswordEncoder encoder() {
        return passwordEncoder;
    }
}
