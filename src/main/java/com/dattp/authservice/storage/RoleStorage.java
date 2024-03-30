package com.dattp.authservice.storage;

import com.dattp.authservice.entity.Role;
import com.dattp.authservice.exception.role.RoleNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RoleStorage extends Storage{
  public List<Role> getRolesFromDB(String username){
    return roleRepository.getRoles(username);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Role save(Role role){
    return roleRepository.save(role);
  }

  public Role findById(Long id){
    return roleRepository.findById(id).orElseThrow(()->new RoleNotFoundException(id));
  }

  public List<Role> getRoles(String username){
    return roleRepository.getRoles(username);
  }
}