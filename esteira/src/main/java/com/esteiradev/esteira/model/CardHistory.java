package com.esteiradev.esteira.model;

import com.esteiradev.esteira.enums.HistoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "CARD_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private HistoryType type;

    @Column(nullable = false)
    private UUID actorId;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @OneToMany(
            mappedBy = "history",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<CardHistoryChange> changes = new ArrayList<>();

    @Transient
    private String message;
}
