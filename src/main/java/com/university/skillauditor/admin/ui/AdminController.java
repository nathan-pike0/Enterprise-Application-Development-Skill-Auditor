package com.university.skillauditor.admin.ui;

import com.university.skillauditor.admin.AdminFacade;
import com.university.skillauditor.admin.ui.commands.AddStaffMemberCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffDepartmentCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffDetailsCommand;
import com.university.skillauditor.admin.ui.commands.UpdateStaffPlacementCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/staff")
@AllArgsConstructor
public class AdminController {

    private final AdminFacade adminFacade;

    @PatchMapping("/{id}/details")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStaffDetails(@PathVariable String id, @RequestBody UpdateStaffDetailsCommand command) {
        adminFacade.updateStaffDetails(id, command);
    }

    @PatchMapping("/{id}/department")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStaffDepartment(@PathVariable String id, @RequestBody UpdateStaffDepartmentCommand command) {
        adminFacade.updateStaffDepartment(id, command);
    }

    @PatchMapping("/{id}/placement")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStaffPlacement(@PathVariable String id, @RequestBody UpdateStaffPlacementCommand command) {
        adminFacade.updateStaffPlacement(id, command);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addStaffMember(@RequestBody AddStaffMemberCommand command) {
        adminFacade.addStaffMember(command);
    }
}