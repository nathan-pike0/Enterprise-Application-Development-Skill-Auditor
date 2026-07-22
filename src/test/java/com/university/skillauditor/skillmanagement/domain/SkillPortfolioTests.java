package com.university.skillauditor.skillmanagement.domain;

import com.university.skillauditor.shared.Identity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SkillPortfolioTests {

    private SkillPortfolio createValidPortfolio() {
        return new SkillPortfolio(
                Identity.generateId(),
                "staff-id",
                "skill-id",
                SkillLevel.BEGINNER,
                null
        );
    }

    private SkillPortfolio createVerifiedPortfolio() {
        SkillPortfolio portfolio = createValidPortfolio();
        portfolio.verifySkill("manager-123");
        return portfolio;
    }

    @Test
    @DisplayName("A valid skill portfolio entry can be created")
    void test01() {
        assertDoesNotThrow(this::createValidPortfolio);
    }

    @Test
    @DisplayName("A new skill portfolio entry always starts as PENDING")
    void test02() {
        SkillPortfolio portfolio = createValidPortfolio();
        assertEquals(PortfolioStatus.PENDING, portfolio.status());
    }

    @Test
    @DisplayName("Cannot create with a null skill level")
    void test03() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new SkillPortfolio(Identity.generateId(), "staff-id", "skill-id", null, null)
        );
        assertEquals(SkillPortfolio.SKILL_LEVEL_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create with a blank staff member id")
    void test04() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new SkillPortfolio(Identity.generateId(), "", "skill-id", SkillLevel.BEGINNER, null)
        );
        assertEquals(SkillPortfolio.STAFF_MEMBER_ID_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create with a blank skill id")
    void test05() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new SkillPortfolio(Identity.generateId(), "staff-id", "", SkillLevel.BEGINNER, null)
        );
        assertEquals(SkillPortfolio.SKILL_ID_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Can verify a pending portfolio entry")
    void test06() {
        SkillPortfolio portfolio = createValidPortfolio();
        assertDoesNotThrow(() -> portfolio.verifySkill("manager-123"));
    }

    @Test
    @DisplayName("Cannot verify a non-pending entry")
    void test07() {
        SkillPortfolio portfolio = createVerifiedPortfolio();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                portfolio.verifySkill("manager-123")
        );
        assertEquals(SkillPortfolio.CANNOT_VERIFY_NOT_PENDING, exception.getMessage());
    }

    @Test
    @DisplayName("Verifying sets status to verified")
    void test08() {
        SkillPortfolio portfolio = createValidPortfolio();
        portfolio.verifySkill("manager-123");
        assertEquals(PortfolioStatus.VERIFIED, portfolio.status());
    }

    @Test
    @DisplayName("Verifying records the manager id")
    void test09() {
        SkillPortfolio portfolio = createValidPortfolio();
        portfolio.verifySkill("manager-123");
        assertEquals("manager-123", portfolio.verifiedById());
    }

    @Test
    @DisplayName("Cannot verify with a blank manager id")
    void test10() {
        SkillPortfolio portfolio = createValidPortfolio();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                portfolio.verifySkill("")
        );
        assertEquals(SkillPortfolio.NOTED_BY_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Can unverify a veriied entry")
    void test11() {
        SkillPortfolio portfolio = createVerifiedPortfolio();
        assertDoesNotThrow(portfolio::unverifySkill);
    }

    @Test
    @DisplayName("Cannot unverify a non-VERIFIED entry")
    void test12() {
        SkillPortfolio portfolio = createValidPortfolio();
        Throwable exception = assertThrows(IllegalArgumentException.class, portfolio::unverifySkill
        );
        assertEquals(SkillPortfolio.CANNOT_UNVERIFY_NOT_VERIFIED, exception.getMessage());
    }

    @Test
    @DisplayName("Unverifying sets status back to pending")
    void test13() {
        SkillPortfolio portfolio = createVerifiedPortfolio();
        portfolio.unverifySkill();
        assertEquals(PortfolioStatus.PENDING, portfolio.status());
    }

    @Test
    @DisplayName("Unverifying clears the verifiedById")
    void test14() {
        SkillPortfolio portfolio = createVerifiedPortfolio();
        portfolio.unverifySkill();
        assertNull(portfolio.verifiedById());
    }

    @Test
    @DisplayName("Can reject a pending entry")
    void test15() {
        SkillPortfolio portfolio = createValidPortfolio();
        assertDoesNotThrow(() -> portfolio.rejectSkill("manager-123"));
    }

    @Test
    @DisplayName("Cannot reject a non-pending entry")
    void test16() {
        SkillPortfolio portfolio = createVerifiedPortfolio();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                portfolio.rejectSkill("manager-123")
        );
        assertEquals(SkillPortfolio.CANNOT_REJECT_NOT_PENDING, exception.getMessage());
    }

    @Test
    @DisplayName("Rejecting sets status to rejected")
    void test17() {
        SkillPortfolio portfolio = createValidPortfolio();
        portfolio.rejectSkill("manager-123");
        assertEquals(PortfolioStatus.REJECTED, portfolio.status());
    }

    @Test
    @DisplayName("Can edit a pending entry")
    void test18() {
        SkillPortfolio portfolio = createValidPortfolio();
        assertDoesNotThrow(() -> portfolio.editSkill(SkillLevel.ADVANCED, null));
    }

    @Test
    @DisplayName("Can edit a verified entry")
    void test19() {
        SkillPortfolio portfolio = createVerifiedPortfolio();
        assertDoesNotThrow(() -> portfolio.editSkill(SkillLevel.ADVANCED, null));
    }

    @Test
    @DisplayName("Can edit a rejected entry")
    void test20() {
        SkillPortfolio portfolio = createValidPortfolio();
        portfolio.rejectSkill("manager-123");
        assertDoesNotThrow(() -> portfolio.editSkill(SkillLevel.ADVANCED, null));
    }

    @Test
    @DisplayName("Editing resets status back to PENDING")
    void test22() {
        SkillPortfolio portfolio = createVerifiedPortfolio();
        portfolio.editSkill(SkillLevel.ADVANCED, null);
        assertEquals(PortfolioStatus.PENDING, portfolio.status());
    }

    @Test
    @DisplayName("Can add a note to a portfolio entry")
    void test23() {
        SkillPortfolio portfolio = createValidPortfolio();
        assertDoesNotThrow(() -> portfolio.addNote("Good skill", "manager-123"));
    }

    @Test
    @DisplayName("Cannot add a blank note")
    void test24() {
        SkillPortfolio portfolio = createValidPortfolio();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                portfolio.addNote("", "manager-123")
        );
        assertEquals(SkillPortfolio.NOTE_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot add a note with a blank manager id")
    void test25() {
        SkillPortfolio portfolio = createValidPortfolio();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                portfolio.addNote("Good skill", "")
        );
        assertEquals(SkillPortfolio.NOTED_BY_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Adding a note increases the notes list size")
    void test26() {
        SkillPortfolio portfolio = createValidPortfolio();
        portfolio.addNote("Good skill", "manager-123");
        assertEquals(1, portfolio.notes().size());
    }
}