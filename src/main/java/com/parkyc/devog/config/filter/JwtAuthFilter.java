package com.parkyc.devog.config.filter;

import com.parkyc.devog.config.WHITE_LIST;
import com.parkyc.devog.security.DevogPrincipal;
import com.parkyc.devog.security.DevogUserDetails;
import com.parkyc.devog.token.dto.TokenClaim;
import com.parkyc.devog.token.provider.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final RequestMatcher whiteListMatcher = new OrRequestMatcher(
            Arrays.stream(WHITE_LIST.URLS)
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
        // 토큰 검증 및 데이터 넣기 작업

        String token = extractToken(request);
        if(token != null){
            TokenClaim claim = tokenProvider.verifyToken(token);

            String loginId = (String) claim.map().get("loginId");
            List<String> roles = (List<String>) claim.map().getOrDefault("roles", List.of());

            DevogUserDetails userDetails = new DevogUserDetails();

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            new DevogPrincipal(loginId, roles),null, authorities
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if(authorization == null || authorization.isBlank()){
            return null;
        }
        if(!authorization.startsWith("Bearer ")){
            return null;
        }

        return authorization.substring("Bearer ".length()).trim();
    }
}
