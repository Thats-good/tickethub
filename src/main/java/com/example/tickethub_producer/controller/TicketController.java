package com.example.tickethub_producer.controller;

import com.example.tickethub_producer.dto.request.ticket.CreateTicketRequestDto;
import com.example.tickethub_producer.dto.request.ticket.CreateUserTicketRequestDto;
import com.example.tickethub_producer.dto.response.ResponseDto;
import com.example.tickethub_producer.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.tickethub_producer.constants.Constants.STATUS_200;
import static com.example.tickethub_producer.constants.Constants.STATUS_201;

@RestController
@RequestMapping(path = "/api")
public class TicketController {

    private final ProxyService proxyService;

    public TicketController(@Qualifier("proxyServiceImpl") ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @PostMapping("/")
    ResponseEntity<ResponseDto> createTicket(@RequestBody CreateTicketRequestDto createTicketRequestDto) {

        proxyService.createTicket(createTicketRequestDto.getUserId(), createTicketRequestDto.getPerformanceId(), LocalDateTime.now(), createTicketRequestDto.getSeatNumber(), createTicketRequestDto.getPayment(), createTicketRequestDto.getJwtToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_201, "Ticket created success."));
    }

    @PostMapping("/ok")
    ResponseEntity<ResponseDto> createUserTicket(@RequestBody CreateUserTicketRequestDto createUserTicketRequestDto) {

//        proxyService.createUserTicket(createUserTicketRequestDto.getUserId(), createUserTicketRequestDto.getJwtToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_201, "UserTicket created success."));
    }

    @GetMapping("/2")
    ResponseEntity<ResponseDto> checkTicket() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200, "Check ticket success."));
    }

    @GetMapping("/3")
    ResponseEntity<ResponseDto> checkToken() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200, "Check Token success."));
    }
}
