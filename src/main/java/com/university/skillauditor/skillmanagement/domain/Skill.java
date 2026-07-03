package com.university.skillauditor.skillmanagement.domain;

import com.university.skillauditor.shared.AggregateRoot;
import com.university.skillauditor.shared.Entity;
import com.university.skillauditor.shared.Identity;

import static com.university.skillauditor.shared.DomainAssertions.argumentNotEmpty;

public class Skill extends Entity<Skill> implements AggregateRoot {
    private String name;
    private String description;
    private String category;
    private SkillStatus status;

    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty";
    public static final String CATEGORY_CANNOT_BE_EMPTY = "Category cannot be empty";
    public static final String STATUS_CANNOT_BE_EMPTY = "Status cannot be empty";
    public static final String STATUS_ALREADY_ACTIVE = "Status cannot already be active";
    public static final String STATUS_ALREADY_INACTIVE = "Status cannot already be inactive";



    public Skill(Identity<Skill> id, String name, String description, String category, SkillStatus status) {
        super(id);
        updateName(name);
        updateDescription(description);
        updateCategory(category);
        updateStatus(status);
    }

    public final void updateName(String name) {
        argumentNotEmpty(name, NAME_CANNOT_BE_EMPTY);
        this.name = name;
    }

    public final void updateDescription(String description) {
        argumentNotEmpty(description, DESCRIPTION_CANNOT_BE_EMPTY);
        this.description = description;
    }

    public final void updateCategory(String category) {
        argumentNotEmpty(category, CATEGORY_CANNOT_BE_EMPTY);
        this.category = category;
    }

    public final void enableSkill() {
        if (this.status == SkillStatus.ACTIVE) {
            throw new IllegalArgumentException(STATUS_ALREADY_ACTIVE);
        }
        this.status = SkillStatus.ACTIVE;
    }

    public final void disableSkill() {
        if (this.status == SkillStatus.INACTIVE) {
            throw new IllegalArgumentException(STATUS_ALREADY_INACTIVE);
        }
        this.status = SkillStatus.INACTIVE;
    }


    public final void updateStatus(SkillStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(STATUS_CANNOT_BE_EMPTY);
        }
        this.status = status;
    }

    public String name() { return name; }
    public String description() { return description; }
    public String category() { return category; }
    public SkillStatus status() { return status; }

}
