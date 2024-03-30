package com.dattp.authservice.exception.role;

public class RoleNotFoundException extends RuntimeException{
  public RoleNotFoundException(String roleName){
    super(roleName + " not found");
  }
  public RoleNotFoundException(Long roleId){
    super("role id = "+ roleId + " not found");
  }
}