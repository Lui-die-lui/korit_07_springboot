package com.example.cardatabase;

import com.example.cardatabase.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
//// 스프링 설정 클래스 임을 표시
//@EnableWebSecurity
//// 스프링 시큐리티 활성화 -> 이거 꼭 달아야함? 다른 수업에서는 안했는데
//public class SecurityConfig {
//    // 사용자 조회 서비스(로그인 시 username으로 사용자 로드)
//    // 생성 될 때마다 계속 추가하기 귀찮으면 @ReqConstructor 그거 쓰기
//    private final UserDetailsServiceImpl userDetailsService;
//    private final AuthenticationFilter authenticationFilter;
//    private final AuthEntryPoint exceptionHandler;
//
//    // 생성자 주입 -> 을 왜함?
//    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler) {
//        this.userDetailsService = userDetailsService;
//        this.authenticationFilter = authenticationFilter;
//        this.exceptionHandler = exceptionHandler;
//    }
//
//    // 전역 인증 매니저 빌더 설정 메서드
//    // Springboot 3 에서는 @Autowired 가 없으면 자동 호출되지 않음
//    // -> 실제로 적용하려면 @Autowired 붙이거나 다른 방식으로 등록
//
//    // 인증에 사용할 UserDetailsService 지정
//    @Autowired
//    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // 비밀번호 검증에 사용할 인코더 지정
//        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Bean // BCryptPasswordEncoder 를 빈으로 등록
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // AuthenticationManager를 직접 Bean으로 등록
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        // 스프링이 구성한 전역 AuthenticationManager 반환
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // CSRF 비활성화 (주로 JWT 기반 API에서 사용)
//        http.csrf(csrf -> csrf.disable())
//                // 세션 전략 설정
//                .sessionManagement(sessionManagement ->
//                        // 세션을 생성 / 사용하지 않음 - 완전 무상태
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                // 인가(접근 권한) 규칙 정의 시작
//                .authorizeHttpRequests(authorizeHttpRequests ->
////                        요청을 무엇과 매칭할지 - post /login은 누구나 접근 허용
//                        authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/login").permitAll()
//                                // 나머지 모든 요청은 인증 필요
//                                .anyRequest().authenticated())
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
////                                                                     // 변수명을 지어서
//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling.authenticationEntryPoint(exceptionHandler)); // 객체 주입
//        // 위 설정으로 SecurityFilterChain 생성
//        return http.build();
//
//        @Bean
//        public CorsConfigurationSource corsConfigurationSource () {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            CorsConfigurationSource config = new CorsConfiguration();
//            config.setAllowedOrigin(Arrays.asList("*"));
//            config.setAllowedMethods(Arrays.asList("*"));
//            config.setAllowedHeaders(Arrays.asList("*"));
//
//            config.setAllowCredentials(false);
//            config.applyPermitDefaultValues();
//
//            source.registerCorsConfiguration("/**", config);
//            return source;
//        }
//    }
//}
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          AuthenticationFilter authenticationFilter,
                          AuthEntryPoint exceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exceptionHandler;
    }

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(exceptionHandler));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
