package com.example.tickethub_producer.dto.request.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckTicketRequestDto {
    private long ticketId;
    private String jwtToken;
}
