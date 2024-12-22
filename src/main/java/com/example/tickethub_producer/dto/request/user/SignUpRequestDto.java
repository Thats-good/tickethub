package com.example.tickethub_producer.dto.request.user;

import com.example.tickethub_producer.entity.User;
import com.example.tickethub_producer.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    private String id;
    private String pw;
    private String name;
    private String email;
    private Role role;
}
