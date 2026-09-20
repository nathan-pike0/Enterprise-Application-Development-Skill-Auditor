package com.university.skillauditor.staffmanagement.domain;

public enum EmploymentType {
    FULL_TIME("Staff Member Working Full Time"),
    PART_TIME("Staff Member Working Part Time"),
    CONTRACT("Staff Member on Contract");

    private final String description;

    EmploymentType(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}
