package com.esteiradev.esteira.utils;

import com.esteiradev.esteira.enums.CardField;
import com.esteiradev.esteira.enums.HistoryType;
import com.esteiradev.esteira.model.history.CardHistory;
import com.esteiradev.esteira.model.history.CardHistoryChange;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CardHistoryMessageBuilder {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String build(CardHistory history){
        if(history.getType() == HistoryType.CREATED){
            return format(history.getOccurredAt()) + " - Card criado";
        }
        else if(history.getType() == HistoryType.STATUS_CHANGED){
            CardHistoryChange  c = history.getChanges().get(0);
            return String.format(
                    format(history.getOccurredAt()) + " - Card movido de %s para %s",
                    c.getOldValue(),
                    c.getNewValue()
            );
        }

        if(history.getType() == HistoryType.UPDATED){
            return buildUpdateMessage(history.getChanges());
        }

        return "";
    }

    private static String buildUpdateMessage(List<CardHistoryChange> changes) {
        if(changes.size() == 1){
            CardHistoryChange change = changes.get(0);
            return String.format(
                    "%s alterado",
                    CardField.from(change.getFieldName()).getLabel()
            );
        }
        return String.format(
                "%d campos alterados",
                changes.size()
        );
    }

    private static String format(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
