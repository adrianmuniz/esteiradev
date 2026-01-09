package com.esteiradev.esteira.model;

import com.esteiradev.esteira.enums.EsteiraType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(
            mappedBy = "esteira",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("priority ASC, position ASC")
    @JsonIgnore
    private List<CardModel> cards = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void syncOrdem() {
        if (this.type != null) {
            this.ordem = this.type.getOrdem();
        }
    }
}
