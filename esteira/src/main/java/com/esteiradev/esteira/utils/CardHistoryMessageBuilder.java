package com.esteiradev.esteira.utils;

import com.esteiradev.esteira.enums.CardField;
import com.esteiradev.esteira.enums.HistoryType;
import com.esteiradev.esteira.model.history.CardHistory;
import com.esteiradev.esteira.model.history.CardHistoryChange;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CardHistoryMessageBuilder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String build(CardHistory history){
        if(history.getType() == HistoryType.CREATED){
            return format(history.getOccurredAt()) + " - Card criado";
        }
        else if(history.getType() == HistoryType.STATUS_CHANGED){
            CardHistoryChange  c = history.getChanges().get(0);
            if(c.getOldValue().equals("CLOSED")){
                return String.format(
                        format(history.getOccurredAt()) + " - Card reaberto",
                        c.getOldValue(),
                        c.getNewValue()
                );
            }
            return String.format(
                    format(history.getOccurredAt()) + " - Card movido de %s para %s",
                    c.getOldValue(),
                    c.getNewValue()
            );
        }
        if(history.getType() == HistoryType.UPDATED){
            return buildUpdateMessage(history);
        }
        return "";
    }

    private static String buildUpdateMessage(CardHistory history) {
        List<CardHistoryChange> changes = history.getChanges();
        if(changes.size() == 1){
            CardHistoryChange c = changes.get(0);
            return String.format(
                    format(history.getOccurredAt()) + " - %s alterado de %s para %s",
                    CardField.fromFieldName(c.getFieldName()),
                    c.getOldValue(),
                    c.getNewValue()
            );
        }

        String camposAlterados = changes.stream()
                .map(c -> CardField.fromFieldName(c.getFieldName()))
                .distinct()
                .collect(Collectors.joining(", "));

        return format(history.getOccurredAt()) + " - Card atualizado: " + camposAlterados;
    }

    private static String format(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
