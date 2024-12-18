package com.dattp.authservice.config.kafka;

//import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicKafkaConfig {
    @Value("${kafka.partition}")
    private int partition;
    public static final String NEW_USER_TOPIC = "com.dattp.restaurant.auth.new_user";
//    public static final String VERIFY_USER_TOPIC = "VERIFY_USER_TOPIC";
//    @Bean
//    public NewTopic newUserTopic(){
//        return TopicBuilder
//          .name(NEW_USER_TOPIC)
//          .partitions(partition)
//          .build();
//    }

//    @Bean
//    public NewTopic verifyUserTopic(){
//        return TopicBuilder.name(VERIFY_USER_TOPIC).build();
//    }
}
