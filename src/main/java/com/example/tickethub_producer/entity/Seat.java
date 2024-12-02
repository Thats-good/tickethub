package com.example.tickethub_producer.entity;

import com.example.tickethub_producer.entity.base.BaseEntity;
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
public class Seat extends BaseEntity {
    @Id
    @Column(name = "seat_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "tag", nullable = false)
    private Tag tag;

    public boolean hasValidTag() {
        return false;
    }

    public void changeTag(Tag tag) {

    }

}
