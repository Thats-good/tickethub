package com.example.tickethub_producer.controller;

import com.example.tickethub_producer.dto.request.user.LoginRequestDto;
import com.example.tickethub_producer.dto.request.user.SignUpRequestDto;
import com.example.tickethub_producer.dto.response.ResponseDto;
import com.example.tickethub_producer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;

import static com.example.tickethub_producer.constants.Constants.STATUS_200;
import static com.example.tickethub_producer.constants.Constants.STATUS_201;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        userService.login(loginRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200, "login success."));
    }

    @PostMapping("/sign-up")
    ResponseEntity<ResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(STATUS_201, "sign-up success."));
    }

}
