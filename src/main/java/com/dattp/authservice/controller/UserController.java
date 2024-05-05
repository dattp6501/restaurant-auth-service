package com.dattp.authservice.controller;

import javax.validation.Valid;

import com.dattp.authservice.anotation.docapi.AddAuthorizedDocAPI;
import com.dattp.authservice.dto.RefreshTokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> register(@RequestBody @Valid UserCreateRequestDTO userReq){
        return ResponseEntity.ok(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", userService.createUser(userReq))
        );
    }

    @GetMapping(value = "/detail", produces = {MediaType.APPLICATION_JSON_VALUE})
    @AddAuthorizedDocAPI
    public ResponseEntity<?> userDetail(){
        return ResponseEntity.ok(userService.getDetail());
    }
}
