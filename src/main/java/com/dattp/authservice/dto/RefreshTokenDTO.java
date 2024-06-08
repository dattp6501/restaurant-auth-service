package com.dattp.authservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RefreshTokenDTO {
  @NotNull(message = "Thiếu refreshToken")
  @NotEmpty(message = "Thiếu refreshToken")
  private String refreshToken;

  private Long userId;

  private Integer otp;

  public RefreshTokenDTO(){}
}