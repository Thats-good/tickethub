package com.example.tickethub_producer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "rt_key" )
    private String key;

    @Column(name = "rt_value")
    private String value;           // Refresh Token String

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
