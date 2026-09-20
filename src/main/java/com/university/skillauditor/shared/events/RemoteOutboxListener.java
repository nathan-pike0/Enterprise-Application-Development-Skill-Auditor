package com.university.skillauditor.shared.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
@Slf4j
public class RemoteOutboxListener {

    private final EventStoreService eventStoreService;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitOutboxRouter rabbitOutboxRouter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Retryable(retryFor = AmqpException.class, maxAttempts = 3, backoff = @Backoff(delay = 500, multiplier = 2.0))
    public void handleRemoteEvent(RemoteEvent event) {
        RabbitOutboxRouter.Destination destination;
        try {
            destination = rabbitOutboxRouter.resolve(event);
        } catch (IllegalArgumentException e) {
            log.error("UNROUTABLE event: {}", e.getMessage());
            eventStoreService.updateStatus(event.id(), StatusOfMessageDelivery.UNROUTABLE, false);
            return;
        }

        rabbitTemplate.convertAndSend(destination.exchange(), destination.routingKey(), event);
        eventStoreService.updateStatus(event.id(), StatusOfMessageDelivery.PUBLISHED, false);
        log.info("Published {} to exchange [{}] routing key [{}]",
                event.getClass().getSimpleName(), destination.exchange(), destination.routingKey());
    }

    @Recover
    public void recover(AmqpException e, RemoteEvent event) {
        log.error("Giving up on event {} after retries exhausted: {}", event, e.getMessage());
        eventStoreService.updateStatus(event.id(), StatusOfMessageDelivery.FAILED, true);
    }
}