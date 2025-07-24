package com.karka_deh.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

// reads from the .env file, if there is, and fills the oauth2 config with the secrets
@Configuration
public class OAuthClientConfig {

  private final Dotenv dotenv = Dotenv.configure()
      .ignoreIfMissing()
      .load();

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    ClientRegistration google = ClientRegistration.withRegistrationId("google")
        .clientId(dotenv.get("GOOGLE_CLIENT_ID"))
        .clientSecret(dotenv.get("GOOGLE_CLIENT_SECRET"))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("{baseUrl}/login/oauth2/code/google")
        .scope("openid", "profile", "email")
        .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
        .tokenUri("https://oauth2.googleapis.com/token")
        .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
        .userNameAttributeName("sub")
        .clientName("Google")
        .build();

    ClientRegistration github = ClientRegistration.withRegistrationId("github")
        .clientId(dotenv.get("GITHUB_CLIENT_ID"))
        .clientSecret(dotenv.get("GITHUB_CLIENT_SECRET"))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("{baseUrl}/login/oauth2/code/github")
        .scope("read:user", "user:email")
        .authorizationUri("https://github.com/login/oauth/authorize")
        .tokenUri("https://github.com/login/oauth/access_token")
        .userInfoUri("https://api.github.com/user")
        .userNameAttributeName("login")
        .clientName("GitHub")
        .build();

    return new InMemoryClientRegistrationRepository(google, github);
  }
}
