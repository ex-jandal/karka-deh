
package com.karka_deh.config;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.Cookie;

import com.karka_deh.jwt.JwtUtil;
import com.karka_deh.jwt.JwtFilter;
import com.karka_deh.jwt.JwtCookieUtil;

@Configuration
public class SecurityConfig {

  private final JwtUtil jwtUtil;
  private final JwtFilter jwtFilter;

  public SecurityConfig(JwtUtil jwtUtil, JwtFilter jwtFilter) {
    this.jwtUtil = jwtUtil;
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationManager authManager) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/auth/**", "/login.html", "/oauth2/**").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth2 -> oauth2
            .successHandler(this::oauth2SuccessHandler)
            .failureHandler((_request, response, exception) -> {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.setContentType("application/json");
              response.getWriter().write("{\"error\": \"OAuth2 login failed\"}");
            }))
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint((request, response, authException) -> {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.setContentType("application/json");
              response.getWriter().write("{\"error\": \"Unauthorized\"}");
            }))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  private void oauth2SuccessHandler(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws java.io.IOException {
    OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
    var attrs = token.getPrincipal().getAttributes();

    // String email = (String) attrs.getOrDefault("email", "unknown@example.com");
    String name = (String) attrs.getOrDefault("name", token.getName());

    String jwt = jwtUtil.generate(name);

    Cookie cookie = JwtCookieUtil.createJwtCookie(jwt, 3600);
    response.addCookie(cookie);

    response.sendRedirect("/home");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("abdullah")
        .password(passwordEncoder().encode("secret123"))
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}
