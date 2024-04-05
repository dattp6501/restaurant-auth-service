package com.dattp.authservice.config;

import com.dattp.authservice.utils.DateUtils;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableScheduling
public class Monitor {
  @Value("${telegram.monitor_bot_token}")
  private String monitorBotToken;

  @Value("${telegram.monitor_bot_chat_id}")
  private String monitorBatChatId;

  @Value("${server.port}")
  private Integer port;

  @Autowired
  private RestTemplate restTemplate;

  @Scheduled(initialDelay = 2000, fixedDelay = 1200000)
  public void isRunning(){
    String message =
      DateUtils.getcurrentLocalDateTime()
        .plusHours(6)
        .format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd"))
        +": AUTH ===> RUNNING";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    JsonObject request = new JsonObject();
    request.addProperty("text", message);
    //request.addProperty("parse_mode", "HTML");
    request.addProperty("disable_web_page_preview", false);
    request.addProperty("chat_id", monitorBatChatId);

    HttpEntity<Object> requestEntity = new HttpEntity<>(request.toString(), headers);

    String url = String.format("https://api.telegram.org/bot%s/sendMessage", monitorBotToken);
    restTemplate.postForObject(url, requestEntity, Object.class);
    // }
  }
}