package com.university.skillauditor.shared.events;

import java.time.LocalDate;

public record StaffDetailsUpdatedEvent(
        Long id,
        LocalDate occurredOn,
        String staffMemberId,
        String firstName,
        String lastName,
        String email
) implements RemoteEvent {

    public StaffDetailsUpdatedEvent(LocalDate occurredOn, String staffMemberId,
                                    String firstName, String lastName, String email) {
        this(null, occurredOn, staffMemberId, firstName, lastName, email);
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public StaffDetailsUpdatedEvent withId(Long newId) {
        return new StaffDetailsUpdatedEvent(newId, occurredOn, staffMemberId, firstName, lastName, email);
    }
}