package com.university.skillauditor.shared.exceptions;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(String skillId) {
        super("Skill not found: " + skillId);
    }
}