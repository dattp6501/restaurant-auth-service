package com.dattp.authservice.service;


//import com.dattp.authservice.config.kafka.TopicKafkaConfig;
import com.dattp.authservice.dto.UserCreateRequestDTO;
import com.dattp.authservice.dto.UserResponseDTO;
import com.dattp.authservice.utils.JSONUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.dattp.authservice.entity.User;

@Service
@Log4j2
public class UserService extends com.dattp.authservice.service.Service {
    public UserResponseDTO saveUser(UserCreateRequestDTO userReq){
        //get data user
        User newUser = new User(userReq);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // luu vao CSDL
        newUser = userStorage.saveToDB(newUser);
        //response
        UserResponseDTO userResp = new UserResponseDTO(newUser);
        // gui thong diep den notification service de gui thong bao xac thuc
//        try {
//            kafkaTemplateString.send(TopicKafkaConfig.NEW_USER_TOPIC, JSONUtils.toJson(userResp));
//        } catch (Exception e) {
//            log.debug("========> saveUser exception {}", e.getMessage());
//        }
        return userResp;
    }
    public void addRoleToUser(String username, String rolename){
        userStorage.addRoleToUser(username, rolename);
    }

    public UserResponseDTO getDetail(){
        User userDb = userStorage.findByIdFromDB(jwtService.getUserId());
      return new UserResponseDTO(userDb);
    }
}
