package com.university.skillauditor.shared.events;

import java.time.LocalDate;

public record NewStaffMemberAddedEvent(
        Long id,
        LocalDate occurredOn,
        String staffMemberId,
        String firstName,
        String lastName,
        String email,
        String hireDate,
        String departmentId,
        String lineManagerId,
        String currentRole,
        String roleStartDate,
        String jobLevel,
        String employmentType
) implements RemoteEvent {

    public NewStaffMemberAddedEvent(LocalDate occurredOn, String staffMemberId, String firstName,
                                    String lastName, String email, String hireDate, String departmentId,
                                    String lineManagerId, String currentRole, String roleStartDate,
                                    String jobLevel, String employmentType) {
        this(null, occurredOn, staffMemberId, firstName, lastName, email, hireDate, departmentId,
                lineManagerId, currentRole, roleStartDate, jobLevel, employmentType);
    }


    @Override
    public Long id() {
        return id;
    }

    @Override
    public NewStaffMemberAddedEvent withId(Long newId) {
        return new NewStaffMemberAddedEvent(newId, occurredOn, staffMemberId, firstName, lastName, email,
                hireDate, departmentId, lineManagerId, currentRole, roleStartDate, jobLevel, employmentType);
    }
}