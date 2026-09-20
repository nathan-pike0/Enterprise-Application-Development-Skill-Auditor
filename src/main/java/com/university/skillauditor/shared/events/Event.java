package com.university.skillauditor.shared.events;

public interface Event {
    Long id();
    Event withId(Long id);
}