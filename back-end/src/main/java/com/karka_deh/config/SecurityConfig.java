package com.karka_deh.config;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

  // for the unused vars in the lambdas
  @SuppressWarnings("unused")
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationManager authManager) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // allow these urls for everyone
            .requestMatchers("/", "/swagger-ui/**", "/auth/**", "/login.html", "/oauth2/**").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // the rest are for authenticated users only, that's what the JwtFilter job is
            .anyRequest().authenticated())
        .oauth2Login(oauth2 -> oauth2
            .successHandler(this::oauth2SuccessHandler)
            .failureHandler((_request, response, _exception) -> {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.setContentType("application/json");
              response.getWriter().write("{\"error\": \"OAuth2 login failed\"}");
            }))
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint((_request, response, _authException) -> {
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

  /**
   * 
   * runs once the oauth2 is good, which if the user
   * logged in using either using Github or Google
   * 
   * all it does is get the name from the oauth2
   * provider, create a jwt token with it, and store
   * it as a cooike
   * 
   * @param request
   * @param response
   * @param authentication
   * @throws java.io.IOException
   *
   */
  private void oauth2SuccessHandler(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws java.io.IOException {
    OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
    var attrs = token.getPrincipal().getAttributes();

    String name = (String) attrs.getOrDefault("name", token.getName());

    String jwt = jwtUtil.generate(name);

    Cookie cookie = JwtCookieUtil.createJwtCookie(jwt, 3600);
    response.addCookie(cookie);

    // NOTE: maybe we can use it later?
    //
    // response.sendRedirect("/home");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
