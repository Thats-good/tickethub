package com.example.tickethub_producer.entity;

import com.example.tickethub_producer.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Performance")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Performance extends BaseEntity {
    @Id
    @Column(name = "performance_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long performanceId;

    @Column(name = "name", nullable = false)
    private String name;
}
