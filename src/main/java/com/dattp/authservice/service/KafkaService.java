package com.dattp.authservice.service;

import com.dattp.authservice.config.kafka.TopicKafkaConfig;
import com.dattp.authservice.dto.UserResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaService {
  @Autowired @Lazy private KafkaTemplate<String, UserResponseDTO> kafkaTemplateUser;

  @Async("taskExecutor")
  public void send(String topic, Object data){
    try {
      if(data instanceof UserResponseDTO) kafkaTemplateUser.send(TopicKafkaConfig.NEW_USER_TOPIC, (UserResponseDTO) data);


      else log.debug("======> KafkaService::send::{}::{} NOT CONFIG SEND KAFKA!!!", topic, data);
      log.debug("======> KafkaService::send::{}::{} SUCCESS!!!", topic, data);
    } catch (Exception e) {
      log.debug("========> KafkaService::send::{}::{}::Exception::{}", topic, data, e.getMessage());
    }
  }
}