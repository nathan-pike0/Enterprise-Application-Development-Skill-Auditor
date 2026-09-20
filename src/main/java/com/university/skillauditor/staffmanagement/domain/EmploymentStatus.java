package com.university.skillauditor.staffmanagement.domain;

public enum EmploymentStatus {
    ACTIVE("Staff Member Working"),
    ON_LEAVE("Staff Member On Leave"),
    TERMINATED("Staff Member Terminated");

    private final String description;

    EmploymentStatus(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}
