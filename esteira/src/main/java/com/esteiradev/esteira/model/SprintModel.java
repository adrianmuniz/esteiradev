package com.esteiradev.esteira.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "TB_SPRINT")
public class SprintModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sprintId;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;
}
