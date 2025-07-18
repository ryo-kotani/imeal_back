package com.imeal.imeal_back.security;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imeal.imeal_back.common.config.CorsProperties;
import com.imeal.imeal_back.user.dto.UserResponse;
import com.imeal.imeal_back.user.service.UserMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  
  private final UserMapper userMapper;
  private final ObjectMapper objectMapper;
  private final CorsProperties corsProperties;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors
        .configurationSource(request -> {
          var corsConfiguration = new CorsConfiguration();
          corsConfiguration.setAllowedOrigins(corsProperties.getAllowedOrigins());
          corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
          corsConfiguration.setAllowCredentials(true);
          corsConfiguration.setAllowedHeaders(List.of("*"));
          return corsConfiguration;
        }))
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
          // ここに記述したパスへリクエストはログインなしで許可
          // "/" → ""に変更
          // "users" → "user"に変更
          .requestMatchers(HttpMethod.POST, "/api/users", "/api/shops").permitAll()
          .requestMatchers(HttpMethod.PUT, "/api/users", "/api/shops/**").permitAll()
          .requestMatchers(HttpMethod.DELETE, "/api/users", "/api/shops/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/api/users", "/api/bases/**", "api/shops/**").permitAll()
          .anyRequest().authenticated())
        .formLogin(login -> login
          .loginProcessingUrl("/api/login")
          .usernameParameter("email")
          .successHandler(authenticationSuccessHandler())
          .failureHandler(authenticationFailureHandler())
          .permitAll())
        .logout(logout -> logout
          .logoutUrl("/api/logout")
          .logoutSuccessHandler(logoutSuccessHandler()));
    return httpSecurity.build();
  }

  // passwordのデフォルトエンコードアルゴリズム
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // login成功時の処理
  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return (request, response, authentication) -> {
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

      UserResponse responseBody = userMapper.toResponse(userDetails.getUser());

      response.setStatus(HttpServletResponse.SC_OK);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      // objectMapperでjsonに変換
      response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    };
  }

  // login失敗時の処理
  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return (request, response, exception) -> {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(objectMapper.writeValueAsString((
        Map.of("messages", List.of("ログインに失敗しました"))
      )));
    };
  }

  // logout時の処理
  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return (request, response, authentication) -> {
      response.setStatus(HttpServletResponse.SC_OK);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(objectMapper.writeValueAsString(
        Map.of("messages", List.of("ログアウトに成功しました"))
      ));
    };
  }
}
