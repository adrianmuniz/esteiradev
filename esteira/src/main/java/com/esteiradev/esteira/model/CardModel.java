package com.esteiradev.esteira.model;

import com.esteiradev.esteira.enums.PriorityEnum;
import com.esteiradev.esteira.enums.StatusCard;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private Integer position;

    private StatusCard status;

    private Integer estimateHours;

    private Integer hoursUsed;

    private Integer hoursRemainning;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreate;

    @LastModifiedDate
    private LocalDateTime  dateUpdated;

    private LocalDateTime  dateResolved;

    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "esteira_id", nullable = false)
    private EsteiraModel esteira;

    @ManyToOne(optional = true ,fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private SprintModel sprint;

    private PriorityEnum priority;
}
