package com.example.tickethub_producer.service;

import com.example.tickethub_producer.dto.request.user.LoginRequestDto;
import com.example.tickethub_producer.dto.request.user.SignUpRequestDto;
import com.example.tickethub_producer.entity.RefreshToken;
import com.example.tickethub_producer.entity.TokenRequestDto;
import com.example.tickethub_producer.entity.User;
import com.example.tickethub_producer.repository.RefreshTokenRepository;
import com.example.tickethub_producer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@RequiredArgsConstructor
public class AuthService implements UserService{
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public long signUp(SignUpRequestDto signUpRequestDto) {
        if(userRepository.existsUserByIdentifierOrEmail(signUpRequestDto.getId(), signUpRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 스튜디오입니다.");
        }

        User user = User.from(signUpRequestDto);
        return userRepository.save(user).getUserId();
    }

    @Transactional
    public AuthTokens login(LoginRequestDto loginRequestDto) {

        return null;
    }


    @Transactional
    public AuthTokens refresh(TokenRequestDto tokenRequestDto) {
        return null;
    }
}