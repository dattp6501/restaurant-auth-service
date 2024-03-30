//package com.dattp.authservice.config.kafka;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class TopicKafkaConfig {
//    public static final String NEW_USER_TOPIC = "NEW_USER_TOPIC";
//    public static final String VERIFY_USER_TOPIC = "VERIFY_USER_TOPIC";
//    @Bean
//    public NewTopic newUserTopic(){
//        return TopicBuilder.name(NEW_USER_TOPIC).build();
//    }
//
//    @Bean
//    public NewTopic verifyUserTopic(){
//        return TopicBuilder.name(VERIFY_USER_TOPIC).build();
//    }
//}
