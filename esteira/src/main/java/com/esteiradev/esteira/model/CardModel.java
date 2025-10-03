package com.esteiradev.esteira.model;

import com.esteiradev.esteira.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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

    private Integer estimateHours;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreate;

    @LastModifiedDate
    private LocalDateTime  dateUpdated;

    private LocalDateTime  dateResolved;

    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "esteira_id")
    private EsteiraModel esteiraModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private SprintModel sprint;
}
