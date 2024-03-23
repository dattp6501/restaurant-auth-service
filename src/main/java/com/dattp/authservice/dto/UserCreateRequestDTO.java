package com.dattp.authservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDTO {
    private Long id;

    @NotNull(message = "Tên đầy đủ(fullname) không được để trống")
    @NotEmpty(message = "Tên đầy đủ(fullname) không được để trống")
    private String fullname;

    @NotNull(message = "Tên đăng nhập(username) không được để trống")
    @NotEmpty(message = "Tên đăng nhập(username) không được để trống")
    private String username;

    @NotNull(message = "Địa chỉ mail(mail) không được để trống")
    @NotEmpty(message = "Địa chỉ mail(mail) không được để trống")
    private String mail;

    @NotNull(message = "Mật khẩu(password) không được để trống")
    @NotEmpty(message = "Mật khẩu(password) không được để trống")
    private String password;

    public UserCreateRequestDTO() {
    }
}
