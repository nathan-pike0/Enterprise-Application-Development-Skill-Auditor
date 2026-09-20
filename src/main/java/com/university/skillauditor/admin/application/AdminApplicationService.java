package com.university.skillauditor.admin.application;

import com.university.skillauditor.admin.ui.commands.AddStaffMemberCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffDetailsCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffDepartmentCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffPlacementCommand;
import com.university.skillauditor.shared.events.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminApplicationService {

    private final DomainEventManager domainEventManager;

    @Transactional
    public void updateStaffDetails(String staffMemberId, UpdateStaffDetailsCommand command) {
        Event event = new StaffDetailsUpdatedEvent(
                LocalDate.now(),
                staffMemberId,
                command.firstName(),
                command.lastName(),
                command.email()
        );
        domainEventManager.manageDomainEvents(this.getClass().getSimpleName(), List.of(event));
    }

    @Transactional
    public void updateStaffDepartment(String staffMemberId, UpdateStaffDepartmentCommand command) {
        Event event = new StaffDepartmentUpdatedEvent(
                LocalDate.now(),
                staffMemberId,
                command.departmentId()
        );
        domainEventManager.manageDomainEvents(this.getClass().getSimpleName(), List.of(event));
    }

    @Transactional
    public void updateStaffPlacement(String staffMemberId, UpdateStaffPlacementCommand command) {
        Event event = new StaffPlacementUpdatedEvent(
                LocalDate.now(),
                staffMemberId,
                command.currentRole(),
                command.jobLevel(),
                command.employmentType()
        );
        domainEventManager.manageDomainEvents(this.getClass().getSimpleName(), List.of(event));
    }

    // firebase uid = staffmember id
    @Transactional
    public void addStaffMember(AddStaffMemberCommand command) {
        if (command.firebaseUid() == null || command.firebaseUid().isBlank()) {
            throw new IllegalArgumentException("Firebase UID cannot be empty");
        }
        String staffMemberId = command.firebaseUid();

        Event event = new NewStaffMemberAddedEvent(
                LocalDate.now(),
                staffMemberId,
                command.firstName(),
                command.lastName(),
                command.email(),
                command.hireDate(),
                command.departmentId(),
                command.lineManagerId(),
                command.currentRole(),
                command.roleStartDate(),
                command.jobLevel(),
                command.employmentType()
        );
        domainEventManager.manageDomainEvents(this.getClass().getSimpleName(), List.of(event));
    }
}