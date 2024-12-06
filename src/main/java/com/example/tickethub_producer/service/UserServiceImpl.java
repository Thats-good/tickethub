package com.example.tickethub_producer.service;

import com.example.tickethub_producer.dto.request.user.LoginRequestDto;
import com.example.tickethub_producer.dto.request.user.SignUpRequestDto;
import com.example.tickethub_producer.entity.User;
import com.example.tickethub_producer.entity.enums.Role;
import com.example.tickethub_producer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Service
//@RequiredArgsConstructor
//@Transactional
//public class UserServiceImpl implements UserService{
//
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder encoder;
//
//    @Override
//    public AuthTokens login(LoginRequestDto loginRequestDto) {
//        Optional<User> userByIdentifierAndPassword = userRepository.findUserByIdentifierAndPassword(id, pw);
//
//        if (userByIdentifierAndPassword.isPresent()) {
//            User user = userByIdentifierAndPassword.get();
//            // TODO JWT 토큰 발행
//
//            return user.getUserId();
//        }
//        return -1;
//    }
//
//    @Override
//    public long signUp(SignUpRequestDto signUpRequestDto) {
//        if(!userRepository.existsUserByIdentifierOrEmail(id, email)){
//            User register = userRepository.findByEmail(email).get();
//            register.encodePassword(encoder.encode(register.getPassword()));
//            return userRepository.save(User.builder()
//                    .identifier(id)
//                    .password(pw)
//                    .email(email)
//                    .role(role)
//                    .build()).getUserId();
//        }
//        return -1;
//    }
//}
