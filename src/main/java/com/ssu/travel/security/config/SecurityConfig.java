package com.ssu.travel.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()

                .antMatchers("/", "/v1/oauth2/authorization/kakao", "/v1/users/signup", "/v1/login").permitAll()
                .antMatchers("/v1/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')");
        //                .antMatchers("/**")
    }
}
