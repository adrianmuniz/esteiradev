package com.esteiradev.esteira.listener;

import com.esteiradev.esteira.enums.HistoryType;
import com.esteiradev.esteira.events.CardCreatedEvent;
import com.esteiradev.esteira.events.CardMovedEvent;
import com.esteiradev.esteira.events.CardUpdatedEvent;
import com.esteiradev.esteira.model.history.CardHistory;
import com.esteiradev.esteira.model.history.CardHistoryChange;
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
    CardHistoryRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCardCreated(CardCreatedEvent event) {

        CardHistory history = CardHistory.builder()
                .cardId(event.cardId())
                .type(HistoryType.CREATED)
                .actorId(event.actorId())
                .occurredAt(LocalDateTime.now())
                .build();

        repository.save(history);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCardMoved(CardMovedEvent event) {

        CardHistory history = CardHistory.builder()
                .cardId(event.cardId())
                .type(HistoryType.STATUS_CHANGED)
                .actorId(event.actorId())
                .occurredAt(LocalDateTime.now())
                .build();

        history.getChanges().add(
                CardHistoryChange.builder()
                        .history(history)
                        .fieldName("esteira")
                        .oldValue(event.oldEsteira())
                        .newValue(event.newEsteira())
                        .build()
        );

        repository.save(history);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCardUpdated(CardUpdatedEvent event) {

        CardHistory history = CardHistory.builder()
                .cardId(event.cardId())
                .type(HistoryType.UPDATED)
                .actorId(event.actorId())
                .occurredAt(LocalDateTime.now())
                .build();

        event.changes().forEach(change -> {
            change.setHistory(history);
            history.getChanges().add(change);
        });

        repository.save(history);
    }
}
