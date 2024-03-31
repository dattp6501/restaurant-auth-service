package com.dattp.authservice.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.dattp.authservice.dto.UserResponseDTO;

@Configuration
public class ProducerKafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${kafka.test}")
    private boolean kafkaTest;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String SASL_JAAS_CONFIG;
    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String SASL_MECHANISM;
    @Value("${spring.kafka.properties.security.protocol}")
    private String SECURITY_PROTOCOL;

    //
    public Map<String, Object> producerConfigJSON(){
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "com.dattp.restaurant.auth.id");

        if(kafkaTest){
          props.put("sasl.jaas.config", SASL_JAAS_CONFIG);
          props.put("sasl.mechanism", SASL_MECHANISM);
          props.put("security.protocol", SECURITY_PROTOCOL);
        }
        return props;
    }
    // user
    @Bean
    public ProducerFactory<String,UserResponseDTO> producerFactoryUser(){
        return new DefaultKafkaProducerFactory<>(producerConfigJSON());
    }
    @Bean
    public KafkaTemplate<String,UserResponseDTO> kafkaTemplateUser(ProducerFactory<String,UserResponseDTO> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
    //
}
