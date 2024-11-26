package com.example.tickethub_producer.service;

import org.springframework.stereotype.Component;

@Component
public class Card implements PaymentStrategy {
    @Override
    public String requestPayment() {
        return "card";
    }
}
