package com.ssu.travel.global.security.jwt.matcher;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FilterSkipMatcher implements RequestMatcher {
    private final OrRequestMatcher skipMatcher;
    private final OrRequestMatcher processingMatcher;

    public FilterSkipMatcher(List<String> skipPaths, List<String> processingPaths) {
        this.skipMatcher = new OrRequestMatcher(
                skipPaths.stream()
                        .map(AntPathRequestMatcher::new)
                        .collect(Collectors.toList())
        );
        this.processingMatcher = new OrRequestMatcher(
                processingPaths.stream()
                        .map(AntPathRequestMatcher::new)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !skipMatcher.matches(request) && processingMatcher.matches(request);
    }
}
