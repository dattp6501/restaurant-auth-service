//package com.dattp.authservice.kafkalistener;
//
//import com.dattp.authservice.config.kafka.TopicKafkaConfig;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableKafka
//@Log4j2
//public class UserKafkaListener {
//  @KafkaListener(id = "myConsumer", topics = TopicKafkaConfig.NEW_USER_TOPIC, groupId = "springboot-group-1", autoStartup = "false")
//  public void listen(String value,
//                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                     @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key
//  ) {
//    log.info(String.format("\n\n Consumed event from topic %s: key = %-10s value = %s \n\n", topic, key, value));
//  }
//}