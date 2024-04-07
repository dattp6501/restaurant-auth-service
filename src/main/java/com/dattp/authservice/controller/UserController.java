package com.dattp.authservice.controller;

import javax.validation.Valid;

import com.dattp.authservice.dto.RefreshTokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dattp.authservice.dto.AuthRequestDTO;
import com.dattp.authservice.dto.ResponseDTO;
import com.dattp.authservice.dto.UserCreateRequestDTO;

@RestController
@RequestMapping("/api/user")
public class UserController extends Controller{
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid AuthRequestDTO authenticationRequest){
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", authenticationService.authenticate(authenticationRequest))
        );
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<ResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenDTO dto){
        return ResponseEntity.ok().body(
          new ResponseDTO(HttpStatus.OK.value(), "Thành công", authenticationService.refreshToken(dto))
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserCreateRequestDTO userReq){
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", userService.createUser(userReq))
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<?> userDetail(){
        return ResponseEntity.ok(userService.getDetail());
    }
}
