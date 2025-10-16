package com.esteiradev.esteira.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
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

    @JsonIgnore
    @Column(nullable = false, length = 30)
    private UUID userId;
}
