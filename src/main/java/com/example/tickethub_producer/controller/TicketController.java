package com.example.tickethub_producer.controller;

import com.example.tickethub_producer.service.ProxyService;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {
    private final ProxyService proxyService;

    public TicketController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }
}
