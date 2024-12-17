package com.example.tickethub_producer.service;

import com.example.tickethub_producer.entity.enums.Role;
import lombok.Builder;

@Builder
public class JwtUserDetails {
    private String email;
    private String identifier;
    private String password;
    private Role role;
}
