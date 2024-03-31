package com.dattp.authservice.storage;

import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.exception.role.RoleNotFoundException;
import com.dattp.authservice.exception.user.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserStorage extends Storage {
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public User saveToDB(User user){
    return userRepository.save(user);
  }

  public User findByIdFromDB(Long userId){
    return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
  }

  public User findByUsernameFromDB(String username){
    return userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void addRoleToUser(String username, String rolename){
    User user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    Role role = roleRepository.findByName(rolename).orElseThrow(()->new RoleNotFoundException(rolename));
    user.getRoles().add(role);
  }


  public List<Role> getDefaultRoleUser(){
    return roleRepository.findRolesByNameIn(
      List.of(
        "ROLE_PRODUCT_ACCESS",
        "ROLE_ORDER_NEW", "ROLE_ORDER_ACCESS", "ROLE_ORDER_UPDATE", "ROLE_ORDER_DELETE"
      )
    );
  }
}