package com.university.skillauditor.shared;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;
import java.time.Instant;

@ToString
@EqualsAndHashCode(callSuper = false)
public final class Identity<T> extends ValueObject {

    public static final String IDENTITY_NOT_EMPTY = "Identity value cannot be empty";

    private final String id;

    private Identity(String id) {
        argumentNotEmpty(id, IDENTITY_NOT_EMPTY);
        this.id = id;
    }

    public String id() {
        return id;
    }

    public static <T> Identity<T> of(String id) {
        return new Identity<>(id);
    }

    public static <T> Identity<T> generateId() {
        return new Identity<>(UUID.randomUUID().toString());
    }
}