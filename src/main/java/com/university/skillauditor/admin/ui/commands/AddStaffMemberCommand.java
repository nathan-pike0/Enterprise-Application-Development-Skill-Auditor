package com.university.skillauditor.admin.ui.commands;

public record AddStaffMemberCommand(
        String firebaseUid,
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
) {}