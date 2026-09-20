package com.university.skillauditor.shared.events;

import java.time.LocalDate;

public record StaffPlacementUpdatedEvent(
        Long id,
        LocalDate occurredOn,
        String staffMemberId,
        String currentRole,
        String jobLevel,
        String employmentType
) implements RemoteEvent {

    public StaffPlacementUpdatedEvent(LocalDate occurredOn, String staffMemberId,
                                      String currentRole, String jobLevel, String employmentType) {
        this(null, occurredOn, staffMemberId, currentRole, jobLevel, employmentType);
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public StaffPlacementUpdatedEvent withId(Long newId) {
        return new StaffPlacementUpdatedEvent(newId, occurredOn, staffMemberId, currentRole, jobLevel, employmentType);
    }
}