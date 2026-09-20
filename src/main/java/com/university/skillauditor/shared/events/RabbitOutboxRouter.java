package com.university.skillauditor.shared.events;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "rabbitmq.outbox")
public class RabbitOutboxRouter {

    public record Destination(String exchange, String routingKey) {
    }

    private Map<String, Destination> bindings = new HashMap<>();

    public Map<String, Destination> getBindings() {
        return bindings;
    }

    public void setBindings(Map<String, Destination> bindings) {
        this.bindings = bindings;
    }

    public Destination resolve(Event event) {
        String className = event.getClass().getName();
        Destination destination = bindings.get(className);

        if (destination == null) {
            throw new IllegalArgumentException("No RabbitMQ destination configured for " + className);
        }
        return destination;
    }
}