package com.dattp.authservice.controller;

import javax.validation.Valid;

import com.dattp.authservice.anotation.docapi.AddAuthorizedDocAPI;
import com.dattp.authservice.dto.RefreshTokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dattp.authservice.dto.AuthRequestDTO;
import com.dattp.authservice.dto.ResponseDTO;
import com.dattp.authservice.dto.UserCreateRequestDTO;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController extends Controller{
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid AuthRequestDTO authenticationRequest){
        return ResponseEntity.ok(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", authenticationService.authenticate(authenticationRequest))
        );
    }

    @PostMapping(value = "/refresh_token", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenDTO dto){
        return ResponseEntity.ok(
          new ResponseDTO(HttpStatus.OK.value(), "Thành công", authenticationService.refreshToken(dto))
        );
    }

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid UserCreateRequestDTO userReq){
        return ResponseEntity.ok(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", userService.createUser(userReq))
        );
    }

    @GetMapping(value = "/detail", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> userDetail(){
        return ResponseEntity.ok(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", userService.getDetail())
        );
    }

    @PostMapping(value = "/logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> logout(){
        userService.logout();
        return ResponseEntity.ok(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", null)
        );
    }

    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    @AddAuthorizedDocAPI
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> genQRCode() throws Throwable {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(authenticationService.genQRCode(jwtService.getUsername()));
    }
}
