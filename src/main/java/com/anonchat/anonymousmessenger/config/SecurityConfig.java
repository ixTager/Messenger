package com.anonchat.anonymousmessenger.config;

import com.anonchat.anonymousmessenger.entity.User;
import com.anonchat.anonymousmessenger.enumeration.UserRole;
import com.anonchat.anonymousmessenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login", "/registration").permitAll()
                                .requestMatchers("/chat", "/chat/**").hasAnyRole(
                                        UserRole.USER.name(), UserRole.ADMIN.name())
                                .requestMatchers("/admin", "/admin/**").hasRole(UserRole.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/chat")
                                .failureUrl("/login?error=true")
                                .loginProcessingUrl("/login")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout=true")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { return new  BCryptPasswordEncoder(); }

    // Идентификация
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findUserByEmailIgnoreCase(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + "not found"));

                Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());

                return new org.springframework.security.core.userdetails.User(
                        user.getFirstName(),
                        user.getPassword(),
                        roles
                );
            }
        };
    }
}
