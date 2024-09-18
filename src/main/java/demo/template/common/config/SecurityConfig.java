package demo.template.common.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

//    private final HttpLoggingFilter httpLoggingFilter;
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//
//    public SecurityConfig(HttpLoggingFilter httpLoggingFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
//        this.httpLoggingFilter = httpLoggingFilter;
//        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
//        this.customAccessDeniedHandler = customAccessDeniedHandler;
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
////                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize ->
//                        authorize
//                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
////                                .requestMatchers(HttpMethod.PATCH, "/v1/admin/rpa").permitAll() // dex -> admin
////                                .requestMatchers(HttpMethod.PATCH, "/v1/admin/manual").permitAll() // planner, rag -> admin
////                                .requestMatchers(HttpMethod.POST, "/v1/admin/manuals").permitAll() // planner -> admin
////                                .requestMatchers(HttpMethod.GET, "/v1/admin/user/*/conversation/*").permitAll() // rag -> admin
//                                .anyRequest().permitAll()
//                );
////                .exceptionHandling(authenticationManager -> authenticationManager
////                        .authenticationEntryPoint(customAuthenticationEntryPoint)
////                        .accessDeniedHandler(customAccessDeniedHandler))
////                .addFilterBefore(httpLoggingFilter, WebAsyncManagerIntegrationFilter.class);
//
//        return httpSecurity.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/auth/**")
                                ).authenticated()
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/h2-console/**")
                                ).permitAll()
                )
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스 spring security 대상에서 제외
        return (web) ->
                web
                        .ignoring()
                        .requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations()
                        );
    }

}
