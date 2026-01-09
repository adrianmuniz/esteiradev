package com.esteiradev.esteira.model;

import com.esteiradev.esteira.enums.EsteiraType;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EsteiraType type;

    @Column(nullable = false)
    private Integer ordem;

    @JsonIgnore
    @Column(nullable = false, length = 30)
    private UUID userId;

    @PrePersist
    @PreUpdate
    private void syncOrdem() {
        if (this.type != null) {
            this.ordem = this.type.getOrdem();
        }
    }
}
