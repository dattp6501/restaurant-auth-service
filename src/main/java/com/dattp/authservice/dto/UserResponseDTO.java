package com.dattp.authservice.dto;

import com.dattp.authservice.entity.Role;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String fullname;
    private String username;
    private String mail;
    private String avatar;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updateAt;

    private List<String> permissions;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        copyProperties(user);
    }

    public void copyProperties(User user) {
        BeanUtils.copyProperties(user, this);
        this.permissions = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        this.createAt = DateUtils.convertToLocalDateTime(user.getCreateAt());
        this.updateAt = DateUtils.convertToLocalDateTime(user.getUpdateAt());
    }
}
