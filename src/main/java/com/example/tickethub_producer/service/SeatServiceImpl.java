package com.example.tickethub_producer.service;

import com.example.tickethub_producer.entity.enums.Tag;
import com.example.tickethub_producer.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public boolean isValidSeatNumber(int seatNumber) {
        return false;
    }

    @Override
    public Tag setSeatTag(Tag tag) {
        return null;
    }
}
