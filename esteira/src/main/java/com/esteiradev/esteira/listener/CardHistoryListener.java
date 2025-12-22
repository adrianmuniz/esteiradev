package com.esteiradev.esteira.listener;

import com.esteiradev.esteira.enums.HistoryType;
import com.esteiradev.esteira.events.EsteiraChangedEvent;
import com.esteiradev.esteira.model.CardHistory;
import com.esteiradev.esteira.repositories.CardHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CardHistoryListener {

    @Autowired
    CardHistoryRepository cardHistoryRepository;

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(EsteiraChangedEvent event) {
        CardHistory cardHistory = CardHistory.builder()
                .cardId(event.cardId())
                .type(HistoryType.STATUS_CHANGED)
                .oldValue(event.oldEsteira().name())
                .newValue(event.newEsteira().name())
                .changedBy(event.changedBy())
                .changedAt(LocalDateTime.now())
                .build();

        cardHistoryRepository.save(cardHistory);
    }
}
