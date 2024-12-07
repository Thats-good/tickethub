package com.example.tickethub_producer.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "authentication", timeToLive = 1000000000L)
public class RedisEntity {

    @Id
    private String email;
    private String accessToken;

    public RedisEntity(String email, String accessToken){
        this.email = email;
        this.accessToken = accessToken;
    }
}
