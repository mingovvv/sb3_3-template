package demo.template.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    private final List<String> returnUri = List.of("/favicon.ico");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (returnUri.stream().anyMatch(uri -> request.getRequestURI().startsWith(uri))) {
            return;
        }

        log.info("filter : {}", "TokenValidatorFilter");

        String token = request.getHeader("Authorization");

        // 토큰 검증 로직
//        if (token != null && validateToken(token)) {
        if (true) {
            // 토큰이 유효할 경우 필터 체인을 계속 진행 (세션 생성 가능)

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    "name-test", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } else {
            throw new ServletException("Invalid Token");
        }

    }

    private boolean validateToken(String token) {
        return true;
    }

}
