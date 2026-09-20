package com.university.skillauditor.shared.exceptions;

public class StaffMemberNotFoundException extends RuntimeException {
    public StaffMemberNotFoundException(String staffMemberId) {
        super("Staff member not found: " + staffMemberId);
    }
}