package com.dattp.authservice.exception.user;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(Long userId){
      super("userId = " + userId + " không tồn tại");
  }

  public UserNotFoundException(String username){
    super("username = " + username + " không tồn tại");
  }
}