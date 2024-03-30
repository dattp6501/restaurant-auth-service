package com.dattp.authservice.service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.dattp.authservice.dto.RefreshTokenDTO;
import com.dattp.authservice.exception.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dattp.authservice.dto.AuthRequestDTO;
import com.dattp.authservice.dto.AuthResponseDTO;
import com.dattp.authservice.entity.User;


@Service
@Log4j2
public class AuthenticationService extends com.dattp.authservice.service.Service {
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO authenticate(AuthRequestDTO authenticationRequest){
        log.debug("=========> AuthenticationService::authenticate::{}", authenticationRequest.getUsername());
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
        User user = userStorage.findByUsernameFromDB(authenticationRequest.getUsername());//kiem tra user
        user.setRoles(roleStorage.getRolesFromDB(user.getUsername()));//lay role
        // tao danh sach role de spring boot quan ly
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(i->authorities.add(
            new SimpleGrantedAuthority(i.getName())
        ));
        String jwtAccessToken = jwtService.generateAccessToken(user, authorities);
        String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        AuthResponseDTO resp = new AuthResponseDTO(jwtAccessToken, jwtRefreshToken, jwtService.getExpirationAccessKey());
        tokenStorage.saveToCache(user.getId(), resp);
        return resp;
    }

    public AuthResponseDTO refreshToken(RefreshTokenDTO dto){
        AuthResponseDTO tokenOld = tokenStorage.get(dto.getUserId());
        if(!tokenOld.getRefreshToken().equals(dto.getRefreshToken())) throw new BadRequestException("Token invalid");

        Map<String, Object> detail = jwtService.getInfoRefreshToken(dto.getRefreshToken());
        // xac thuc thanh cong
        String username = (String) detail.get("username");
        User user = userStorage.findByUsernameFromDB(username);//kiem tra user
        user.setRoles(roleStorage.getRolesFromDB(user.getUsername()));//lay role
        // tao danh sach role de spring boot quan ly
        Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream()
          .map(e->new SimpleGrantedAuthority(e.getName()))
          .collect(Collectors.toList());
        String jwtAccessToken = jwtService.generateAccessToken(user, authorities);
        String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        AuthResponseDTO resp = new AuthResponseDTO(jwtAccessToken, jwtRefreshToken, jwtService.getExpirationAccessKey());
        tokenStorage.saveToCache(user.getId(), resp);
        return resp;
    }

    public Map<String, Object> verify(String accessToken) throws AccessDeniedException {
        Map<String, Object> detail = jwtService.getDetail(accessToken);

        AuthResponseDTO tokenOld = tokenStorage.get((Long) detail.get("id"));
        if(!tokenOld.getAccessToken().equals(accessToken)) throw new AccessDeniedException("Access Denied");

        return detail;
    }
}