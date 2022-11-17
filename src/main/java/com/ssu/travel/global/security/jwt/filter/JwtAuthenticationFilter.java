package com.ssu.travel.global.security.jwt.filter;

import com.ssu.travel.global.security.jwt.token.JwtPreAuthenticationToken;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    public JwtAuthenticationFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 인증 헤더가 없는 경우 익명 사용자로 간주 (Anonymous Authentication)
        if (Objects.isNull(authorizationHeader)) {
            return new AnonymousAuthenticationToken(UUID.randomUUID().toString(),
                    "anonymous",
                    List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());

        JwtPreAuthenticationToken preAuthenticationToken = new JwtPreAuthenticationToken(token);
        return this.getAuthenticationManager().authenticate(preAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authResult);
        SecurityContextHolder.setContext(securityContext);
        chain.doFilter(request, response);
    }
}
