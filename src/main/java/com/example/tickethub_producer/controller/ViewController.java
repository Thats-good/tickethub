package com.example.tickethub_producer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/view")
public class ViewController {

    @GetMapping("/main")
    String index(){
        return "index";
    }

    @GetMapping("/login")
    String login(){
        return "login";
    }

    @GetMapping("/register")
    String register(){
        return "register";
    }

    @GetMapping("/myPage")
    String myPage(){
        return "myPage";
    }

    @GetMapping("/checkTicket")
    String check(){
        return "checkTicket";
    }
}
