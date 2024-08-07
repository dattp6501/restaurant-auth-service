package com.dattp.authservice.service;


import com.dattp.authservice.storage.RoleStorage;
import com.dattp.authservice.storage.TokenStorage;
import com.dattp.authservice.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class Service {
  @Autowired @Lazy protected RestTemplate restTemplate;
  @Autowired @Lazy protected PasswordEncoder passwordEncoder;
  @Autowired @Lazy protected JWTService jwtService;

  @Autowired @Lazy protected UserStorage userStorage;
  @Autowired @Lazy protected RoleStorage roleStorage;
  @Autowired @Lazy protected TokenStorage tokenStorage;

  @Autowired @Lazy protected KafkaService kafkaService;
}
