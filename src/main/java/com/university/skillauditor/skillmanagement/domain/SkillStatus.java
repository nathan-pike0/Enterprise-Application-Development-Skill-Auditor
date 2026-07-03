package com.university.skillauditor.skillmanagement.domain;

public enum SkillStatus {
    ACTIVE("Assignable Skill"),
    INACTIVE("Non-Assignable Skill");

    private final String description;

    SkillStatus(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}
