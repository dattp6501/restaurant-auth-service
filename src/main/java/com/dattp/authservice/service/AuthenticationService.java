package com.dattp.authservice.service;

import com.dattp.authservice.dto.AuthRequestDTO;
import com.dattp.authservice.dto.AuthResponseDTO;
import com.dattp.authservice.dto.RefreshTokenDTO;
import com.dattp.authservice.entity.User;
import com.dattp.authservice.exception.BadRequestException;
import com.dattp.authservice.utils.GoogleAuthenticatorUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.file.AccessDeniedException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
public class AuthenticationService extends com.dattp.authservice.service.Service {
    @Value("${domain_name}")
    private String domainName;
    @Value("${google_authenticator.enable}")
    private boolean googleAuthenticatorEnable;
    @Value("${google_authenticator.time}")
    private int googleAuthenticatorTime;
    @Value("${google_authenticator.application_name}")
    private String googleAuthenticatorAppName;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO authenticate(AuthRequestDTO authenticationRequest) {
        log.debug("=========> AuthenticationService::authenticate::{}", authenticationRequest.getUsername());
        // xac thuc
        /*  
        tao UsernamePasswordAuthenticationToken, sau do authenticationManager se dua doi tuong cho AuthenticationProvider
        tuong ung da duoc cau hinh de xu ly:
        - neu thanh cong: AuthenticationProvider sẽ trả về một đối tượng Authentication mới, thường chứa thông tin chi tiết về người dùng (ví dụ: đối tượng UserDetails)
        - nau that bai: nó sẽ ném ra một ngoại lệ AuthenticationException
        */
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        // xac thuc thanh cong
        User user = userStorage.findByUsernameFromDB(authenticationRequest.getUsername());//kiem tra user
        //google authenticator
        if (user.getIsEnable2Fa().equals(true)) {
            validateOTP(user.getGoogleAuthenticatorSecretKey(), authenticationRequest.getOtp());
        }
        //
        user.setRoles(roleStorage.getRolesFromDB(user.getUsername()));//lay role
        // tao danh sach role de spring boot quan ly
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
        String jwtAccessToken = jwtService.generateAccessToken(user, authorities);
        String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        AuthResponseDTO resp = new AuthResponseDTO(jwtAccessToken, jwtRefreshToken, jwtService.getExpirationAccessKey());
        tokenStorage.saveToCache(user.getId(), resp);
        return resp;
    }

    public AuthResponseDTO refreshToken(RefreshTokenDTO dto) {
        AuthResponseDTO tokenOld = tokenStorage.get(dto.getUserId());
        if (!tokenOld.getRefreshToken().equals(dto.getRefreshToken())) throw new BadRequestException("Token invalid");

        Map<String, Object> detail = jwtService.getInfoRefreshToken(dto.getRefreshToken());
        // xac thuc thanh cong
        String username = (String) detail.get("username");
        User user = userStorage.findByUsernameFromDB(username);//kiem tra user
        //google authenticator
        if (user.getIsEnable2Fa().equals(true)) {
            validateOTP(user.getGoogleAuthenticatorSecretKey(), dto.getOtp());
        }
        //
        user.setRoles(roleStorage.getRolesFromDB(user.getUsername()));//lay role
        // tao danh sach role de spring boot quan ly
        Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(e -> new SimpleGrantedAuthority(e.getName())).collect(Collectors.toList());
        String jwtAccessToken = jwtService.generateAccessToken(user, authorities);
        String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        AuthResponseDTO resp = new AuthResponseDTO(jwtAccessToken, jwtRefreshToken, jwtService.getExpirationAccessKey());
        tokenStorage.saveToCache(user.getId(), resp);
        return resp;
    }

    public Map<String, Object> verify(String accessToken) throws AccessDeniedException {
        Map<String, Object> detail = jwtService.getDetail(accessToken);
        //kiem tra token tren cache
//        AuthResponseDTO tokenOld = tokenStorage.get((Long) detail.get("id"));
//        if (!tokenOld.getAccessToken().equals(accessToken)) throw new AccessDeniedException("Access Denied");

        return detail;
    }

    //google authenticator
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public byte[] genQRCode(String username) throws Throwable {
        User user = userStorage.findByUsernameFromDB(username);
        if (Objects.isNull(user.getGoogleAuthenticatorSecretKey())) {
            user.setGoogleAuthenticatorSecretKey(GoogleAuthenticatorUtils.generateSecret());
            userStorage.saveToDB(user);
        }
        String code = generateOTPProtocol(user.getUsername(), user.getGoogleAuthenticatorSecretKey());
        return Base64.getDecoder().decode(GoogleAuthenticatorUtils.generateQRCode(code));
    }

    public void validateOTP(String userSecret, Integer OTP) {
        if (!googleAuthenticatorEnable) return;
        if (Objects.isNull(OTP)) throw new BadRequestException("TOTP code is mandatory");
        if (StringUtils.hasText(userSecret)) {
            try {
                if (!GoogleAuthenticatorUtils.verifyCode(userSecret, OTP, googleAuthenticatorTime)) {
                    throw new BadCredentialsException("Invalid TOTP code");
                }
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                throw new InternalAuthenticationServiceException("TOTP code verification failed", e);
            }
        }
    }

    public String generateOTPProtocol(String username, String secret) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", username, username + "@" + domainName, secret, googleAuthenticatorAppName);
    }
}