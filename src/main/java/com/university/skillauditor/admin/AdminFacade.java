package com.university.skillauditor.admin;

import com.university.skillauditor.admin.application.AdminApplicationService;
import com.university.skillauditor.admin.ui.commands.AddStaffMemberCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffDepartmentCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffDetailsCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffPlacementCommand;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdminFacade {

    private final AdminApplicationService adminApplicationService;

    @PreAuthorize("hasRole('ADMIN')")
    public void addStaffMember(AddStaffMemberCommand command) {
        adminApplicationService.addStaffMember(command);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void updateStaffDetails(String staffMemberId, UpdateStaffDetailsCommand command) {
        adminApplicationService.updateStaffDetails(staffMemberId, command);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void updateStaffDepartment(String staffMemberId, UpdateStaffDepartmentCommand command) {
        adminApplicationService.updateStaffDepartment(staffMemberId, command);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void updateStaffPlacement(String staffMemberId, UpdateStaffPlacementCommand command) {
        adminApplicationService.updateStaffPlacement(staffMemberId, command);
    }
}