package com.example.tickethub_producer.controller;

import com.example.tickethub_producer.dto.request.ticket.CheckTicketRequestDto;
import com.example.tickethub_producer.dto.request.ticket.CheckTokenRequestDto;
import com.example.tickethub_producer.dto.request.ticket.CreateTicketRequestDto;
import com.example.tickethub_producer.dto.request.ticket.CheckUserTicketRequestDto;
import com.example.tickethub_producer.dto.response.ResponseDto;
import com.example.tickethub_producer.entity.PerformanceTicket;
import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.tickethub_producer.constants.Constants.STATUS_200;
import static com.example.tickethub_producer.constants.Constants.STATUS_201;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/ticket")
public class TicketController {

    private final ProxyService proxyService;

//    public TicketController(@Qualifier("proxyServiceImpl") ProxyService proxyService) {
//        this.proxyService = proxyService;
//    }

    @PostMapping("/create")
    ResponseEntity<ResponseDto> createTicket(@RequestBody CreateTicketRequestDto createTicketRequestDto) {

        proxyService.createTicket(createTicketRequestDto.getPerformanceId(), LocalDateTime.now(), createTicketRequestDto.getSeatNumber(), createTicketRequestDto.getPayment(), createTicketRequestDto.getJwtToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_201, "Ticket created success.", null));
    }

    @PostMapping("/checkUserTicket")
    ResponseEntity<ResponseDto> checkUserTicket(@RequestBody CheckUserTicketRequestDto checkUserTicketRequestDto) {

//        proxyService.createUserTicket(createUserTicketRequestDto.getUserId(), createUserTicketRequestDto.getJwtToken());
        List<PerformanceTicket> tickets = proxyService.checkUserTicket(checkUserTicketRequestDto.getJwtToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_201, "UserTicket created success.", tickets));
    }

    @PostMapping("/checkTicket")
    ResponseEntity<ResponseDto> checkTicket(@RequestBody CheckTicketRequestDto checkTicketRequestDto) {
        String token = proxyService.checkTicket(checkTicketRequestDto.getTicketId(), checkTicketRequestDto.getJwtToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200, "Check ticket success.", token));
    }

    @PostMapping("/checkToken")
    ResponseEntity<ResponseDto> checkToken(@RequestBody CheckTokenRequestDto checkTokenRequestDto) {
        boolean isValid = proxyService.checkToken(checkTokenRequestDto.getTicketId(), checkTokenRequestDto.getToken(), checkTokenRequestDto.getJwtToken());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(STATUS_200, "Check Token success.", isValid));
    }
}
