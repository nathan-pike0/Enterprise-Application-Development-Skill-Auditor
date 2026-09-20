package com.university.skillauditor.skillmanagement.domain;

import com.university.skillauditor.shared.AggregateRoot;
import com.university.skillauditor.shared.Identity;
import com.university.skillauditor.shared.events.SkillVerifiedEvent;

import static com.university.skillauditor.shared.DomainAssertions.argumentNotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SkillPortfolio extends AggregateRoot<SkillPortfolio> {

    private final String staffMemberId;
    private final String skillId;
    private SkillLevel skillLevel;
    private LocalDate expiryDate;
    private PortfolioStatus status;
    private String verifiedById;
    private String rejectedById;
    private final List<PortfolioNote> notes;
    private final LocalDateTime submittedAt;
    private LocalDateTime updatedAt;

    public static final String SKILL_ID_CANNOT_BE_NULL = "Skill ID cannot be empty";
    public static final String STAFF_MEMBER_ID_CANNOT_BE_NULL = "Staff member ID cannot be empty";
    public static final String SKILL_LEVEL_CANNOT_BE_NULL = "Skill Level cannot be empty";
    public static final String CANNOT_VERIFY_NOT_PENDING = "Cannot verify a non-pending skill";
    public static final String CANNOT_UNVERIFY_NOT_VERIFIED = "Cannot un-verify a non-verified skill";
    public static final String CANNOT_REJECT_NOT_PENDING = "Cannot reject a non-pending skill";
    public static final String CANNOT_EDIT_EXPIRED = "Cannot edit an expired skill";
    public static final String NOTE_CANNOT_BE_EMPTY = "Note cannot be empty";
    public static final String NOTED_BY_CANNOT_BE_NULL = "Noted by ID cannot be null";

    private SkillPortfolio(Identity<SkillPortfolio> id,
                           String staffMemberId,
                           String skillId,
                           SkillLevel skillLevel,
                           LocalDate expiryDate,
                           PortfolioStatus status,
                           String verifiedById,
                           String rejectedById,
                           LocalDateTime submittedAt,
                           LocalDateTime updatedAt) {
        super(id);
        argumentNotEmpty(staffMemberId, STAFF_MEMBER_ID_CANNOT_BE_NULL);
        argumentNotEmpty(skillId, SKILL_ID_CANNOT_BE_NULL);
        if (skillLevel == null) {
            throw new IllegalArgumentException(SKILL_LEVEL_CANNOT_BE_NULL);
        }
        this.staffMemberId = staffMemberId;
        this.skillId = skillId;
        this.skillLevel = skillLevel;
        this.expiryDate = expiryDate;
        this.status = status;
        this.verifiedById = verifiedById;
        this.rejectedById = rejectedById;
        this.notes = new ArrayList<>();
        this.submittedAt = submittedAt;
        this.updatedAt = updatedAt;
    }

    public SkillPortfolio(Identity<SkillPortfolio> id,
                          String staffMemberId,
                          String skillId,
                          SkillLevel skillLevel,
                          LocalDate expiryDate) {
        this(id, staffMemberId, skillId, skillLevel, expiryDate,
                PortfolioStatus.PENDING, null, null, LocalDateTime.now(), LocalDateTime.now());
    }

    public static SkillPortfolio reconstitute(Identity<SkillPortfolio> id,
                                              String staffMemberId,
                                              String skillId,
                                              SkillLevel skillLevel,
                                              LocalDate expiryDate,
                                              PortfolioStatus status,
                                              String verifiedById,
                                              String rejectedById,
                                              LocalDateTime submittedAt,
                                              LocalDateTime updatedAt) {
        return new SkillPortfolio(id, staffMemberId, skillId, skillLevel, expiryDate,
                status, verifiedById, rejectedById, submittedAt, updatedAt);
    }

    public final void verifySkill(String verifiedById) {
        if (status != PortfolioStatus.PENDING) {
            throw new IllegalArgumentException(CANNOT_VERIFY_NOT_PENDING);
        }
        argumentNotEmpty(verifiedById, NOTED_BY_CANNOT_BE_NULL);

        this.status = PortfolioStatus.VERIFIED;
        this.verifiedById = verifiedById;
        this.updatedAt = LocalDateTime.now();

        addDomainEvent(new SkillVerifiedEvent(
                LocalDate.now(),
                this.id.id(),
                this.skillId,
                this.staffMemberId,
                verifiedById
        ));
    }
    public final void unverifySkill() {
        if (status != PortfolioStatus.VERIFIED) {
            throw new IllegalArgumentException(CANNOT_UNVERIFY_NOT_VERIFIED);
        }
        this.status = PortfolioStatus.PENDING;
        this.verifiedById = null;
        this.updatedAt = LocalDateTime.now();
    }

    public final void rejectSkill(String rejectedById) {
        if (status != PortfolioStatus.PENDING) {
            throw new IllegalArgumentException(CANNOT_REJECT_NOT_PENDING);
        }
        argumentNotEmpty(rejectedById, NOTED_BY_CANNOT_BE_NULL);

        this.status = PortfolioStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
        this.rejectedById = rejectedById;
    }

    public final void editSkill(SkillLevel skillLevel, LocalDate expiryDate) {
        if (status == PortfolioStatus.EXPIRED) {
            throw new IllegalArgumentException(CANNOT_EDIT_EXPIRED);
        }
        this.status = PortfolioStatus.PENDING;
        this.skillLevel = skillLevel;
        this.expiryDate = expiryDate;
        this.updatedAt = LocalDateTime.now();
    }

    public final void addNote(String note, String addedById) {
        argumentNotEmpty(note, NOTE_CANNOT_BE_EMPTY);
        argumentNotEmpty(addedById, NOTED_BY_CANNOT_BE_NULL);

        Identity<PortfolioNote> noteId = Identity.generateId();

        PortfolioNote portfolioNote = new PortfolioNote(
                noteId,
                this.id.id(),
                note,
                addedById
        );

        this.notes.add(portfolioNote);
        this.updatedAt = LocalDateTime.now();
    }

    public String staffMemberId() { return staffMemberId; }
    public String skillId() { return skillId; }
    public SkillLevel skillLevel() { return skillLevel; }
    public LocalDate expiryDate() { return expiryDate; }
    public PortfolioStatus status() { return status; }
    public String verifiedById() { return verifiedById; }
    public List<PortfolioNote> notes() { return notes; }
    public LocalDateTime submittedAt() { return submittedAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
    public String rejectedById() { return rejectedById; }
}