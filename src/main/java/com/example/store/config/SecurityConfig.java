package com.example.store.config;

import com.example.store.exception.CustomAccessDeniedHandler;
import com.example.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /*
         * Here, the order is reversed. It first restricts access to the add endpoints
         * for categories, manufacturers,
         * and products to only “ADMIN” role users, and then it allows all other
         * requests to be permitted.
         * This is the correct approach as it ensures that the more specific URL
         * patterns
         * are secured before the more general ones.
         * 
         * In security configurations, the order of the rules matters because the first
         * rule that matches
         * a given URL pattern is the one that’s applied. Therefore, the second approach
         * is the safer
         * and more correct configuration. Always define the most specific rules first
         * before
         * the more general ones to ensure proper access control.
         */
        http.authorizeHttpRequests(
                auths -> {
                    auths.requestMatchers(
                            "/categories/add",
                            "/manufacturers/add",
                            "/products/add",
                            "/orders/status/**")
                            
                            
                            .hasRole("ADMIN");
                    // ====== Login for check cart ADD 2024/06/11 PhucHT START ======//

                    auths.requestMatchers("/orders/**").authenticated(); // Login khi check cart


                    // ====== Login for check cart ADD 2024/06/11 PhucHT END ======//

                    auths.requestMatchers("/**").permitAll();
                })

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))

                // .httpBasic(Customizer.withDefaults());
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/user/**")) // Disable
    // CSRF for /api/user

    /**
     * The userDetailsService bean is a crucial part of the authentication mechanism
     * in Spring Security,
     * providing user details to the framework so it can perform authentication
     * checks.
     *
     * @return User
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}