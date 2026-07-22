package com.university.skillauditor.skillmanagement.domain;

public enum SkillLevel {
    BEGINNER("Beginner level"),
    INTERMEDIATE("Intermediate level"),
    ADVANCED("Advanced level"),
    EXPERT("Expert level");


    private final String description;

    SkillLevel(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}
