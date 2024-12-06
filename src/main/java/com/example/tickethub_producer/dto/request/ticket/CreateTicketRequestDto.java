package com.example.tickethub_producer.dto.request.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTicketRequestDto {
    private long userId;
    private long performanceId;
    private int seatNumber;
    private String payment;
    private String jwtToken;
}
