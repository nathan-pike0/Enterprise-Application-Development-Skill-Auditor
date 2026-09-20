package com.university.skillauditor.shared.events;

import java.time.LocalDate;

public record StaffDepartmentUpdatedEvent(
        Long id,
        LocalDate occurredOn,
        String staffMemberId,
        String departmentId
) implements RemoteEvent {

    public StaffDepartmentUpdatedEvent(LocalDate occurredOn, String staffMemberId, String departmentId) {
        this(null, occurredOn, staffMemberId, departmentId);
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public StaffDepartmentUpdatedEvent withId(Long newId) {
        return new StaffDepartmentUpdatedEvent(newId, occurredOn, staffMemberId, departmentId);
    }
}