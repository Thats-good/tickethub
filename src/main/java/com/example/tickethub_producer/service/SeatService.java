package com.example.tickethub_producer.service;


import com.example.tickethub_producer.entity.enums.Tag;

public interface SeatService {

    boolean isValidSeatNumber(int seatNumber);

    Tag setSeatTag(Tag tag);
}
