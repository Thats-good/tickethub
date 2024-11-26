package com.example.tickethub_producer.service;

import org.springframework.stereotype.Component;

@Component
public class Cash implements PaymentStrategy {
    @Override
    public String requestPayment() {
        return "cash";
    }
}
