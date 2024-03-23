package com.dattp.authservice.service;


import com.dattp.authservice.dto.UserCreateRequestDTO;
import com.dattp.authservice.dto.UserResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserService extends com.dattp.authservice.service.Service {
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public UserResponseDTO saveUser(UserCreateRequestDTO userReq){
        //get data user
        User newUser = new User(userReq);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // luu vao CSDL
        newUser = userRepository.save(newUser);
        //response
        UserResponseDTO userResp = new UserResponseDTO(newUser);
        // gui thong diep den notification service de gui thong bao xac thuc
//        try {
////            kafkaTemplateUser.send("newUser", userResp);
//        } catch (Exception e) {
//            log.debug("========> saveUser exception {}", e.getMessage());
//        }
        return userResp;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void addRoleToUser(String username, String rolename){
        User user = userRepository.findByUsername(username).orElseThrow();
        Role role = roleRepository.findByName(rolename).orElseThrow();
        user.getRoles().add(role);
    }

    public UserResponseDTO getDetail(){
        User userDb = userRepository.findById(jwtService.getUserId()).orElseThrow();
        UserResponseDTO response = new UserResponseDTO(userDb);
        return response;
    }
}
