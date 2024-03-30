package com.dattp.authservice.storage;

import com.dattp.authservice.repository.RoleRepository;
import com.dattp.authservice.repository.UserRepository;
import com.dattp.authservice.service.JWTService;
import com.dattp.authservice.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Storage {
  @Autowired protected UserRepository userRepository;
  @Autowired protected RoleRepository roleRepository;

  @Autowired protected RedisService redisService;
  @Autowired protected JWTService jwtService;
}