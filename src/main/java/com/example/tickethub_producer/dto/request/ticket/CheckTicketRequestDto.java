package com.example.tickethub_producer.dto.request.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckTicketRequestDto {
    private long ticketId;
    private String jwtToken;
}
