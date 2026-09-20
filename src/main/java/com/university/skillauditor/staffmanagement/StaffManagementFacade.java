package com.university.skillauditor.staffmanagement;

import com.university.skillauditor.staffmanagement.api.StaffMemberResponse;
import com.university.skillauditor.staffmanagement.application.StaffMemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StaffManagementFacade {

    private final StaffMemberService staffMemberService;

    @PreAuthorize("hasAnyRole('MANAGER','SKILL_MANAGER','ADMIN')")
    public List<StaffMemberResponse> getAllStaffMembers() {
        return staffMemberService.getAllStaffMembers();
    }

    @PreAuthorize("hasAnyRole('MANAGER','SKILL_MANAGER','ADMIN')")
    public StaffMemberResponse getStaffMemberById(String id) {
        return staffMemberService.getStaffMemberById(id);
    }
}