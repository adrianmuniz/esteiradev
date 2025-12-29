package com.esteiradev.esteira.listener;

import com.esteiradev.esteira.enums.HistoryType;
import com.esteiradev.esteira.events.CardCreatedEvent;
import com.esteiradev.esteira.events.EsteiraChangedEvent;
import com.esteiradev.esteira.events.UpdatedCardEvent;
import com.esteiradev.esteira.model.CardHistory;
import com.esteiradev.esteira.repositories.CardHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CardHistoryListener {

    @Autowired
    CardHistoryRepository cardHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(EsteiraChangedEvent event) {
        CardHistory cardHistory = CardHistory.builder()
                .cardId(event.cardId())
                .typeHistory(HistoryType.CARD_MOVED)
                .oldEsteira(event.oldEsteira().name())
                .newEsteira(event.newEsteira().name())
                .changedBy(event.changedBy())
                .changedAt(LocalDateTime.now())
                .createdBy(event.changedBy())
                .build();

        cardHistoryRepository.save(cardHistory);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(CardCreatedEvent event) {
        CardHistory cardHistory = CardHistory.builder()
                .cardId(event.cardId())
                .typeHistory(HistoryType.CARD_CREATED)
                .cratedAt(LocalDateTime.now())
                .createdBy(event.createdBy())
                .build();

        cardHistoryRepository.save(cardHistory);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UpdatedCardEvent event) {
        CardHistory cardHistory = CardHistory.builder()
                .cardId(event.cardId())
                .typeHistory(HistoryType.CARD_UPDATED)
                .changedBy(event.changedBy())
                .createdBy(event.changedBy())
                .changedAt(LocalDateTime.now())
                .cratedAt(event.createdAt())
                .build();

        cardHistoryRepository.save(cardHistory);
    }
}
