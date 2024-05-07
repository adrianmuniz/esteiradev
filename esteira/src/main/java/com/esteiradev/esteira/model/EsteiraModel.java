package com.esteiradev.esteira.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "TB_ESTEIRA")
public class EsteiraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID esteiraId;

    @Column(nullable = false, length = 30)
    private String titulo;
}
