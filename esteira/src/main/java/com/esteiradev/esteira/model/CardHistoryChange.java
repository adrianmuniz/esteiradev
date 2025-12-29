package com.esteiradev.esteira.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "CARD_HISTORY_CHANGE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardHistoryChange {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String fieldName;

    @Column(length = 500)
    private String oldValue;

    @Column(length = 500)
    private String newValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "history_id")
    @JsonIgnore
    private CardHistory history;
}
