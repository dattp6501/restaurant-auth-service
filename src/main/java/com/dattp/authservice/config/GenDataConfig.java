package com.dattp.authservice.config;

import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.repository.UserRepository;
import com.dattp.authservice.service.RoleService;
import com.dattp.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GenDataConfig{
  @Autowired private RoleService roleService;
  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  public void genData(){
//    Role roleAdmin = roleService.saveRole(new Role(null, "ROLE_ADMIN", true));
//    // nhom quyen nguoi dung
//    Role roleOrder1 = roleService.saveRole(new Role(null, "ROLE_ORDER_NEW", true));
//    Role roleOrder2 = roleService.saveRole(new Role(null, "ROLE_ORDER_ACCESS", true));
//    Role roleOrder3 = roleService.saveRole(new Role(null, "ROLE_ORDER_UPDATE", true));
//    Role roleOrder4 = roleService.saveRole(new Role(null, "ROLE_ORDER_DELETE", true));
//
//    Role roleProduct1 = roleService.saveRole(new Role(null, "ROLE_PRODUCT_ACCESS", true));
//    Role roleProduct2 = roleService.saveRole(new Role(null, "ROLE_PRODUCT_NEW", true));
//    Role roleProduct3 = roleService.saveRole(new Role(null, "ROLE_PRODUCT_UPDATE", true));
//    Role roleProduct4 = roleService.saveRole(new Role(null, "ROLE_PRODUCT_DELETE", true));
//
//    User user1 = userRepository.save(new User(null, "Khách hàng 1", "kh1", passwordEncoder.encode("1"), "kh1@gmail.com", new ArrayList<>()));
//    User user2 = userRepository.save(new User(null, "manager", "manager", passwordEncoder.encode("manager"), "manager@gmail.com", new ArrayList<>()));
//    User user3 = userRepository.save(new User(null, "admin", "admin", passwordEncoder.encode("admin"), "admin@gmail.com", new ArrayList<>()));
//    // khach hang
//    userService.addRoleToUser(user1.getUsername(), roleProduct1.getName());
//    userService.addRoleToUser(user1.getUsername(), roleOrder1.getName());
//    userService.addRoleToUser(user1.getUsername(), roleOrder2.getName());
//    // manager
//    userService.addRoleToUser(user2.getUsername(), roleProduct1.getName());
//    userService.addRoleToUser(user2.getUsername(), roleProduct2.getName());
//    userService.addRoleToUser(user2.getUsername(), roleProduct3.getName());
//    userService.addRoleToUser(user2.getUsername(), roleProduct4.getName());
//    userService.addRoleToUser(user2.getUsername(), roleOrder2.getName());
//    userService.addRoleToUser(user2.getUsername(), roleOrder3.getName());
//    userService.addRoleToUser(user2.getUsername(), roleOrder4.getName());
//    // admin
//    userService.addRoleToUser(user3.getUsername(), roleAdmin.getName());
  }
}
