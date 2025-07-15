package com.esteiradev.esteira.model;

import com.esteiradev.esteira.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "TB_CARD")
public class CardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    private Integer position;

    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "esteira_id")
    private EsteiraModel esteiraModel;
}
