package com.university.skillauditor.skillmanagement.domain;

import com.university.skillauditor.shared.Identity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkillTests {

    private Skill createValidSkill() {
        return new Skill(
                Identity.generateId(),
                "Java Programming",
                "Core Java development skills",
                "Software Development",
                SkillStatus.ACTIVE
        );
    }

    @Test
    @DisplayName("A valid skill can be created")
    void test01() {
        assertDoesNotThrow(this::createValidSkill);
    }

    @Test
    @DisplayName("Cannot create a skill with a blank name")
    void test02() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Skill(Identity.generateId(), "", "description", "category", SkillStatus.ACTIVE)
        );
        assertEquals(Skill.NAME_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a skill with a blank description")
    void test03() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Skill(Identity.generateId(), "name", "", "category", SkillStatus.ACTIVE)
        );
        assertEquals(Skill.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a skill with a blank category")
    void test04() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Skill(Identity.generateId(), "name", "description", "", SkillStatus.ACTIVE)
        );
        assertEquals(Skill.CATEGORY_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a skill with a null status")
    void test05() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Skill(Identity.generateId(), "name", "description", "category", null)
        );
        assertEquals(Skill.STATUS_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Can enable a skill that is currently inactive")
    void test06() {
        Skill skill = new Skill(Identity.generateId(), "name", "description", "category", SkillStatus.INACTIVE);
        assertDoesNotThrow(skill::enableSkill);
    }

    @Test
    @DisplayName("Enabling an inactive skill sets its status to ACTIVE")
    void test07() {
        Skill skill = new Skill(Identity.generateId(), "name", "description", "category", SkillStatus.INACTIVE);
        skill.enableSkill();
        assertEquals(SkillStatus.ACTIVE, skill.status());
    }

    @Test
    @DisplayName("Cannot enable a skill that is already active")
    void test08() {
        Skill skill = createValidSkill();
        Throwable exception = assertThrows(IllegalArgumentException.class, skill::enableSkill);
        assertEquals(Skill.STATUS_ALREADY_ACTIVE, exception.getMessage());
    }

    @Test
    @DisplayName("Can disable a skill that is currently active")
    void test09() {
        Skill skill = createValidSkill();
        assertDoesNotThrow(skill::disableSkill);
    }

    @Test
    @DisplayName("Disabling an active skill sets its status to INACTIVE")
    void test10() {
        Skill skill = createValidSkill();
        skill.disableSkill();
        assertEquals(SkillStatus.INACTIVE, skill.status());
    }

    @Test
    @DisplayName("Cannot disable a skill that is already inactive")
    void test11() {
        Skill skill = new Skill(Identity.generateId(), "name", "description", "category", SkillStatus.INACTIVE);
        Throwable exception = assertThrows(IllegalArgumentException.class, skill::disableSkill);
        assertEquals(Skill.STATUS_ALREADY_INACTIVE, exception.getMessage());
    }

    @Test
    @DisplayName("Can update a skill's name to a valid value")
    void test12() {
        Skill skill = createValidSkill();
        assertDoesNotThrow(() -> skill.updateName("New Name"));
    }

    @Test
    @DisplayName("Cannot update a skill's name to a blank value")
    void test13() {
        Skill skill = createValidSkill();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> skill.updateName(""));

        assertEquals(Skill.NAME_CANNOT_BE_EMPTY, exception.getMessage());
    }
}