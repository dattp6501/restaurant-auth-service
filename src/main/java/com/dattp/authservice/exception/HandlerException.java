package com.dattp.authservice.exception;

import com.dattp.authservice.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dattp.authservice.dto.ResponseDTO;

@RestControllerAdvice
public class HandlerException {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO> handlerBadCredentialsException(BadCredentialsException e){
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
            new ResponseDTO(HttpStatus.FORBIDDEN.value(),"Sai tên đăng nhập hoặc mật khẩu",null)
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO> handlerBadCredentialsException(AccessDeniedException e){
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
          new ResponseDTO(HttpStatus.FORBIDDEN.value(),"Sai tên đăng nhập hoặc mật khẩu",null)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseDTO> handlerBindException(BindException e){
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
            new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getAllErrors().get(0).getDefaultMessage(),null)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO> handlerBadRequestException(BadRequestException e){
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
            new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null)
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDTO> handlerUserNotFoundException(UserNotFoundException e){
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
          new ResponseDTO(HttpStatus.FORBIDDEN.value(),e.getMessage(),null)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
            new ResponseDTO(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null)
        );
    }
}
