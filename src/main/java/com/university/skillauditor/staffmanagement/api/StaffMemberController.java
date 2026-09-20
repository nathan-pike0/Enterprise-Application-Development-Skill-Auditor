package com.university.skillauditor.staffmanagement.api;

import com.university.skillauditor.staffmanagement.StaffManagementFacade;
import com.university.skillauditor.staffmanagement.application.StaffMemberService;
import com.university.skillauditor.staffmanagement.domain.EmploymentType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@AllArgsConstructor
public class StaffMemberController {

    private StaffManagementFacade staffManagementFacade;
    @GetMapping
    public List<StaffMemberResponse> getAllStaffMembers() {
        return staffManagementFacade.getAllStaffMembers();
    }

    @GetMapping("/{id}")
    public StaffMemberResponse getStaffMemberById(@PathVariable String id) {
        return staffManagementFacade.getStaffMemberById(id);
    }
}