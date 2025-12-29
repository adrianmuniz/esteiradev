package com.esteiradev.esteira.listener;

import com.esteiradev.esteira.enums.HistoryType;
import com.esteiradev.esteira.events.CardCreatedEvent;
import com.esteiradev.esteira.events.EsteiraChangedEvent;
import com.esteiradev.esteira.model.CardHistory;
import com.esteiradev.esteira.repositories.CardHistoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
                .type(HistoryType.STATUS_CHANGED)
                .oldValue(event.oldEsteira().name())
                .newValue(event.newEsteira().name())
                .changedBy(event.changedBy())
                .changedAt(LocalDateTime.now())
                .build();

        cardHistoryRepository.save(cardHistory);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(CardCreatedEvent event) {
        CardHistory cardHistory = CardHistory.builder()
                .cardId(event.cardId())
                .type(HistoryType.CARD_CREATED)
                .cratedAt(LocalDateTime.now())
                .createdBy(event.createdBy())
                .build();

        cardHistoryRepository.save(cardHistory);
    }
}
