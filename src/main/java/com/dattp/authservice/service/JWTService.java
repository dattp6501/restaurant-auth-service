package com.dattp.authservice.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.dattp.authservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dattp.authservice.entity.User;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-accesstoken}")
    private long EXPIRATION_ACCESSTOKEN;

    @Value("${jwt.expiration-refreshtoken}")
    private long EXPIRATION_REFRESHTOKEN;

    public String generateAccessToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        System.out.println(DateUtils.getCurrentMils()+EXPIRATION_ACCESSTOKEN);
        return JWT.create()
            .withSubject(user.getId()+"")
            .withExpiresAt(new Date(DateUtils.getCurrentMils()+EXPIRATION_ACCESSTOKEN))
            .withClaim("id", user.getId())
            .withClaim("username", user.getUsername())
            .withClaim("mail", user.getMail())
            .withClaim("fullname", user.getFullname())
            .withClaim(
                "roles", 
                authorities.stream().map(
                        GrantedAuthority::getAuthority
                    )
                    .collect(Collectors.toList()
                )
            )
            .sign(algorithm);
    }

    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_REFRESHTOKEN))
            .sign(algorithm);
    }
    /*
    * Cac phuong thuc duoi chi su dung khi xac thuc tnanh cong
    * */
    public Long getUserId(){
        return (Long) getDetails().get("id");
    }

    public String getFullname(){
        return getDetails().get("fullname").toString();
    }

    public String getUsername(){
        return getDetails().get("username").toString();
    }

    public String getMail(){
        return getDetails().get("mail").toString();
    }

    @SuppressWarnings(value="unchecked")
    private Map<String, Object> getDetails(){
        return (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
    //

}
