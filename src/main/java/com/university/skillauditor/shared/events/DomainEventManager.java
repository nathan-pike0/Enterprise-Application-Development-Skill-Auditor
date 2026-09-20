package com.university.skillauditor.shared.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DomainEventManager {
    private final ApplicationEventPublisher eventPublisher;
    private final EventStoreService eventStoreService;

    @Transactional
    public void manageDomainEvents(String sourceContext, List<Event> events) {
        for (Event event : events) {
            log.info("{} -> {}", sourceContext, event);
            EventStoreEntity savedEvent = eventStoreService.append(event);
            eventPublisher.publishEvent(event.withId(savedEvent.getId()));
        }
    }
}