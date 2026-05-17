package com.parkyc.devog.config;

import com.parkyc.devog.config.filter.JwtAuthFilter;
import com.parkyc.devog.config.filter.TestUserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter authFilter;
//    private final TestUserFilter testUserFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // @Priority(1)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 테스트용 모든 경로 오픈 및 임시계정 추가 */
//        http.csrf(csrf -> csrf.disable())
//            .formLogin(form -> form.disable())
//            .sessionManagement(session ->
//                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/**").permitAll()
//                    .anyRequest().authenticated()
//            )
//            .oauth2Login(oauth ->
//                    oauth.defaultSuccessUrl("/api/lgn/oauth/callback", true))
//            .addFilterBefore(testUserFilter, UsernamePasswordAuthenticationFilter.class);

        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(WhiteList.URLS).permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
