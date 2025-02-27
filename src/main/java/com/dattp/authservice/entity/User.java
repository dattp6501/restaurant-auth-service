package com.dattp.authservice.entity;

import com.dattp.authservice.dto.UserCreateRequestDTO;
import com.dattp.authservice.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", schema = "auth")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_enable_2fa")
    private Boolean isEnable2Fa;

    @Column(name = "google_authenticator_secret_key", unique = true)
    private String googleAuthenticatorSecretKey;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "update_at")
    private Long updateAt;

    @ManyToMany
    @JoinTable(
            name = "role_user", schema = "auth",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public User() {
        super();
    }

    @PrePersist
    protected void onCreate() {
        this.isEnable2Fa = false;
        this.createAt = this.updateAt = DateUtils.getCurrentMils();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = DateUtils.getCurrentMils();
    }

    public User(Long id, String fullname, String username, String password, String mail, List<Role> roles) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.roles = roles;
    }

    public User(UserCreateRequestDTO userReq) {
        copyProperties(userReq);
    }

    public void copyProperties(UserCreateRequestDTO userReq) {
        BeanUtils.copyProperties(userReq, this);
        this.createAt = DateUtils.getCurrentMils();
        this.updateAt = DateUtils.getCurrentMils();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(i -> {
            authorities.add(new SimpleGrantedAuthority(i.getName()));
        });
        return List.of(new SimpleGrantedAuthority(authorities.toString()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}