package com.example.tickethub_producer.dto.request.user;

import com.example.tickethub_producer.entity.User;
import com.example.tickethub_producer.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    private String id;
    private String pw;
    private String email;
    private Role role;

    public User user(PasswordEncoder passwordEncoder) {
        return User.builder()
                .identifier(id)
                .password(passwordEncoder.encode(pw))
                .role(role)
                .email(email)
                .build();
    }

}
