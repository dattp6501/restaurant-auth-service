package com.dattp.authservice.config.redis;

public class RedisKeyConfig {
  public static final String PREFIX_APP = "restaurant::";

  public static final String PREFIX_AUTH = PREFIX_APP + "auth::";

  public static final String FREFIX_TOKEN = PREFIX_AUTH + "token::";

  public static String genKeyToken(Long userId){
    return FREFIX_TOKEN + userId;
  }
}
