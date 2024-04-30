package com.example.library.config;

import com.example.library.model.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/", "/home", "/library/author", "/library/book").permitAll()
                                .requestMatchers("/library/author", "/library/book").hasAuthority(Role.READER.name())
                                .requestMatchers("/library/author/add", "/library/book/add").hasAuthority(Role.EDITOR.name())
                                .anyRequest().authenticated()
                )
                .formLogin(
                        (form) -> form
                                .loginPage("/login")
                                .permitAll()
                )
                .logout(
                        (logout) -> logout.permitAll()
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public JwtTokenFilter jwtTokenFilter() {
//        return new JwtTokenFilter(jwtTokenProvider);
//    }
}