package com.parkyc.devog.config.filter;

import com.parkyc.devog.config.WhiteList;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final RequestMatcher whiteListMatcher = new OrRequestMatcher(
            Arrays.stream(WhiteList.URLS)
                    .<RequestMatcher>map(PathPatternRequestMatcher::pathPattern)
                    .toList()
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return whiteListMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(request, response);
    }
}
