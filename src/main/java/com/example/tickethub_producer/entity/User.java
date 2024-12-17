package com.example.tickethub_producer.entity;

import com.example.tickethub_producer.dto.request.user.SignUpRequestDto;
import com.example.tickethub_producer.entity.base.BaseEntity;
import com.example.tickethub_producer.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "jwt_token")
    private String jwtToken;

    public static User from(SignUpRequestDto signUpRequestDto) {
        return User.builder()
                .name(signUpRequestDto.getName())
                .identifier(signUpRequestDto.getId())
                .password(signUpRequestDto.getPw())
                .email(signUpRequestDto.getEmail())
                .role(Role.NORMAL)
                .build();
    }
}
