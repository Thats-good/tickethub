package com.example.tickethub_producer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {

    private String statusCode;

    private String statusMessage;

    private Object data;
}
