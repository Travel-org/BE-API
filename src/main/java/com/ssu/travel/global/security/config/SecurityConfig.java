package com.ssu.travel.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.security.jwt.JwtProperties;
import com.ssu.travel.global.security.jwt.service.JwtService;
import com.ssu.travel.global.security.jwt.filter.JwtAuthenticationFilter;
import com.ssu.travel.global.security.jwt.filter.JwtRefreshFilter;
import com.ssu.travel.global.security.jwt.handler.CustomLogoutHandler;
import com.ssu.travel.global.security.jwt.handler.CustomLogoutSuccessHandler;
import com.ssu.travel.global.security.jwt.handler.JwtAuthenticationFailureHandler;
import com.ssu.travel.global.security.jwt.handler.JwtTokenRenewalExceptionHandler;
import com.ssu.travel.global.security.jwt.matcher.FilterSkipMatcher;
import com.ssu.travel.global.security.jwt.matcher.LogoutRequestMatcher;
import com.ssu.travel.global.security.jwt.provider.JwtAuthenticationProvider;
import com.ssu.travel.global.security.jwt.service.RefreshTokenService;
import com.ssu.travel.global.security.oauth2.service.CustomOAuth2UserService;
import java.util.List;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProperties jwtProperties;
    private final JwtService<User> jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtTokenRenewalExceptionHandler jwtTokenRenewalExceptionHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final ObjectMapper objectMapper;

    @Value("${redirect.url}")
    private String redirectUrl;

    public Filter jwtRefreshFilter() throws Exception {
        return new JwtRefreshFilter(refreshTokenService, jwtService, jwtProperties, jwtTokenRenewalExceptionHandler,
                new AntPathRequestMatcher("/api/refresh"), objectMapper);
    }

    public Filter jwtAuthenticationFilter() throws Exception {
        FilterSkipMatcher filterSkipMatcher = new FilterSkipMatcher(
                List.of("/api/refresh", "/api/logout"),
                List.of("/api/**")
        );
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(filterSkipMatcher);
        jwtAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);

        return jwtAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.httpBasic().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.cors();

        //logout
        http.logout()
                .logoutRequestMatcher(new LogoutRequestMatcher())
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll();

        // OAuth2 filter chain configuration
        http.oauth2Login()
                .successHandler(customOAuth2UserService::onAuthenticationSuccess)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        // JWT Authentication filter chain configuration
        http.addFilterBefore(jwtAuthenticationFilter(), OAuth2AuthorizationRequestRedirectFilter.class);
        http.addFilterBefore(jwtRefreshFilter(), JwtAuthenticationFilter.class);

        // TODO: url antMatcher로 구체화
        // URL security
        http.authorizeRequests()
                .expressionHandler(expressionHandler())
                .antMatchers("/api/a").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/api/user").authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin(redirectUrl);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // role hierarchy expression handler
    @Bean
    public SecurityExpressionHandler<FilterInvocation> expressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_USER > ROLE_ANONYMOUS");
        return roleHierarchy;
    }
}
