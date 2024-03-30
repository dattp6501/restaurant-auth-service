package com.dattp.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.repository.RoleRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService extends com.dattp.authservice.service.Service {
    public Role saveRole(Role role){
        return roleStorage.save(role);
    }

    public Role getByID(Long id){
        return roleStorage.findById(id);
    }
    public List<Role> getRoles(User user){
        return roleStorage.getRoles(user.getUsername());
    }
}
