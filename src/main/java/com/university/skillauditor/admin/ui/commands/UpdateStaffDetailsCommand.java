package com.university.skillauditor.admin.ui.commands;

public record UpdateStaffDetailsCommand(
        String firstName,
        String lastName,
        String email
) {}