package com.dattp.authservice.controller;

import com.dattp.authservice.service.AuthenticationService;
import com.dattp.authservice.service.JWTService;
import com.dattp.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
  @Autowired @Lazy protected UserService userService;
  @Autowired @Lazy protected JWTService jwtService;

  @Autowired @Lazy protected AuthenticationService authenticationService;
}