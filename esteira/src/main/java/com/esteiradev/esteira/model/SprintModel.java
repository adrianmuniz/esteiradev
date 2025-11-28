package com.esteiradev.esteira.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_SPRINT")
public class SprintModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sprintId;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;
}
