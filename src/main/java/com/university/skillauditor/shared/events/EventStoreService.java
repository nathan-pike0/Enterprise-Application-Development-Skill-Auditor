package com.university.skillauditor.shared.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EventStoreService {

    private final EventStoreRepository eventStoreRepository;
    private final JsonMapper jsonMapper;

    public EventStoreEntity append(Event event) {
        EventStoreEntity entity = new EventStoreEntity();
        entity.setOccurredOn(LocalDate.now());
        entity.setEventType(event.getClass().getName());
        entity.setStatus(StatusOfMessageDelivery.PENDING.name());
        entity.setRetryCount(0);

        try {
            entity.setEventBody(jsonMapper.writeValueAsString(event));
        } catch (Exception e) {
            log.error("Failed to serialise event {}: {}", event.getClass().getName(), e.getMessage());
            entity.setEventBody(event.toString());
        }

        return eventStoreRepository.save(entity);
    }

    public void updateStatus(Long eventStoreId, StatusOfMessageDelivery status, boolean incrementRetry) {
        Optional<EventStoreEntity> result = eventStoreRepository.findById(eventStoreId);
        if (result.isEmpty()) {
            log.warn("Attempted to update status for unknown event store id {}", eventStoreId);
            return;
        }


        EventStoreEntity entity = result.get();
        entity.setStatus(status.name());
        if (incrementRetry) {
            entity.setRetryCount(entity.getRetryCount() + 1);
        }
        eventStoreRepository.save(entity);
    }
}