package com.dattp.authservice.service;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dattp.authservice.dto.AuthRequestDTO;
import com.dattp.authservice.dto.AuthResponseDTO;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.repository.RoleRepository;
import com.dattp.authservice.repository.UserRepository;


@Service
@Log4j2
public class AuthenticationService {
    @Value("${jwt.expiration-accesstoken}")
    private long EXPIRATION_ACCESSTOKEN;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO authenticate(AuthRequestDTO authenticationRequest){
        log.debug("=========> Username = {} login", authenticationRequest.getUsername());
        // xac thuc
        /*  
        tao UsernamePasswordAuthenticationToken, sau do authenticationManager se dua doi tuong cho AuthenticationProvider
        tuong ung da duoc cau hinh de xu ly:
        - neu thanh cong: AuthenticationProvider sẽ trả về một đối tượng Authentication mới, thường chứa thông tin chi tiết về người dùng (ví dụ: đối tượng UserDetails)
        - nau that bai: nó sẽ ném ra một ngoại lệ AuthenticationException
        */
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        // xac thuc thanh cong
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();//kiem tra user
        if(user!=null){
            user.setRoles(roleRepository.getRoles(user.getUsername()));//lay role
        }
        // tao danh sach role de spring boot quan ly
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(i->authorities.add(
            new SimpleGrantedAuthority(i.getName())
        ));
        String jwtAccessToken = jwtService.generateAccessToken(user, authorities);
        String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        return new AuthResponseDTO(jwtAccessToken, jwtRefreshToken, EXPIRATION_ACCESSTOKEN);
    }
}