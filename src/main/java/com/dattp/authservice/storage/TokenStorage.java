package com.dattp.authservice.storage;

import com.dattp.authservice.config.redis.RedisKeyConfig;
import com.dattp.authservice.dto.AuthResponseDTO;
import com.dattp.authservice.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TokenStorage extends Storage{

  public void saveToCache(Long userId, AuthResponseDTO token){
      redisService.setEntity(RedisKeyConfig.genKeyToken(userId), token, jwtService.getExpirationRefreshKey());
  }

  public void removeCache(Long userId){
    redisService.delete(RedisKeyConfig.genKeyToken(userId));
  }

  public AuthResponseDTO get(Long userId){
    AuthResponseDTO token = redisService.getEntity(RedisKeyConfig.genKeyToken(userId), AuthResponseDTO.class);
    if(Objects.isNull(token)) throw new BadRequestException("token invalid");
    return token;
  }
}