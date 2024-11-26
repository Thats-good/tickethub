package com.example.tickethub_producer.entity;

import com.example.tickethub_producer.entity.enums.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seatId;
    int seatNumber;
    Tag tag;

    public boolean hasValidTag() {
        return false;
    }

    public void changeTag(Tag tag) {

    }

}
