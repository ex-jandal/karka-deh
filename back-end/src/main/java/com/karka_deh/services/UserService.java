package com.karka_deh.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.karka_deh.models.mappers.UserMapper;
import com.karka_deh.models.responses.auth.SignupResponse;
import com.karka_deh.repos.UserRepo;

@Service
public class UserService implements UserDetailsService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  private final UserMapper userMapper;

  public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, UserMapper userMapper) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
  }

  public Optional<UUID> getUserId(String username) {
    return this.userRepo.findByUsername(username).map(user -> {
      return user.getId();
    });
  }

  public boolean existsByUsername(String username) {
    return this.userRepo.existsByUsername(username);
  }

  public SignupResponse createUser(String username, String password) {
    var hashedPassword = this.passwordEncoder.encode(password);
    var userEntity = this.userRepo.createUser(username, hashedPassword);

    return this.userMapper.toSignupResponse(userEntity);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = this.userRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPasswordHash())
        .build();

  }
}
