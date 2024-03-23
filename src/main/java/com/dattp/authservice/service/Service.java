package com.dattp.authservice.service;

import com.dattp.authservice.repository.RoleRepository;
import com.dattp.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.stereotype.Service
public class Service {
  @Autowired @Lazy protected PasswordEncoder passwordEncoder;
  @Autowired @Lazy protected UserRepository userRepository;
  @Autowired @Lazy protected RoleRepository roleRepository;
  @Autowired @Lazy protected JWTService jwtService;
}
