package ro.iss.agorainretea.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ro.iss.agorainretea.service.LoginUtilityService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    LoginUtilityService loginUtilityService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        // @formatter:off
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers( "/signup").permitAll()
                        .requestMatchers( "/home/**").permitAll()
                        .requestMatchers( "/").permitAll()
                        .requestMatchers( "/auth/signup").permitAll()
                        .requestMatchers( "/login_redirect").permitAll()
                        .requestMatchers("/styles/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/articles/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        // @formatter:on

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = (userName) -> {
            ro.iss.agorainretea.domain.User user = loginUtilityService.findMatch(userName);
            return User.builder()
                    .username(user.getEmail())
                    .password(passwordEncoder().encode(user.getPassword()))
                    .roles("plebe")
                    .build();
        };
        return userDetailsService;
    }
}
