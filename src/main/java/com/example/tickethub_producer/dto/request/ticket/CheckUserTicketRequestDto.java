package com.example.tickethub_producer.dto.request.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckUserTicketRequestDto {
    private String jwtToken;
}
