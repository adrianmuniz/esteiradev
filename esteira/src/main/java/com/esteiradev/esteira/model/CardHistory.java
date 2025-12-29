package com.esteiradev.esteira.model;

import com.esteiradev.esteira.enums.HistoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_CARD_HISTORY")
@Builder
public class CardHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID cardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HistoryType typeHistory;

    @Column(length = 255)
    private String oldEsteira;

    @Column(length = 255)
    private String newEsteira;

    @Column
    private UUID changedBy;

    @Column
    private LocalDateTime changedAt;

    @Column
    private LocalDateTime cratedAt;

    @Column(nullable = false)
    private UUID createdBy;
}
