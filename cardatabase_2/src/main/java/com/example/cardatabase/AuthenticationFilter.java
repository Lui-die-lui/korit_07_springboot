package com.example.cardatabase;

import com.example.cardatabase.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// Caused by: java.sql.SQLSyntaxErrorException: (conn=509) Unknown SEQUENCE: 'cardb.owner_seq' 떠도 돌아감
import java.io.IOException;
import java.util.Collections;
// extends - 확장
// OncePerRequestFilter - 추상 클래스

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

//    public AuthenticationFilter(JwtService jwtService) {
//        this.jwtService = jwtService;
//    } @RequiredArgsConstructor - 있으면 따로 생성하지 않아도 되는 코드


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // 토큰 가져오기 - json web string
        String jws = request.getHeader(HttpHeaders.AUTHORIZATION); // 로그인 한 곳에서 가져옴(Headers에 토큰이 있음)
        // 토큰 검증 및 사용자 가져오기
        if (jws != null) {
            String user = jwtService.getAuthUser(request); // 여기 작성 방식이 매우 유사합니다.
            // 인증
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, null, Collections.emptyList()
            );
            // 객체 생성 - 객체 대입
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
