package com.example.tickethub_producer.service;

public interface UserService {
    long login(String id, String pw);
    long signUp(String id, String pw, String email, String role);
}
