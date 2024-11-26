package com.example.tickethub_producer.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final Map<String, PaymentStrategy> paymentStrategies;

    // String은 PaymentStrategy의 빈 이름으로 스프링이 자동 주입
    // 따라서 전략을 선택하려면 요청시 빈 이름으로 요청
    public PaymentServiceImpl(Map<String, PaymentStrategy> paymentStrategy) {
        this.paymentStrategies = paymentStrategy;
    }

    @Override
    public String requestPayment(String paymentCode) {
        PaymentStrategy paymentStrategy = paymentStrategies.get(paymentCode);
        return paymentStrategy.requestPayment();
    }
}
