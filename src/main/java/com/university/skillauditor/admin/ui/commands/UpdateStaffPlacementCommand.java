package com.university.skillauditor.admin.ui.commands;

public record UpdateStaffPlacementCommand(
        String currentRole,
        String jobLevel,
        String employmentType
) {}