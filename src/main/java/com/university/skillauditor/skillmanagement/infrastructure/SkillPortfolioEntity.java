package com.university.skillauditor.skillmanagement.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "skill_portfolio")
@Getter
@Setter
public class SkillPortfolioEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "staff_member_id")
    private String staffMemberId;

    @Column(name = "skill_id")
    private String skillId;

    @Column(name = "skill_level")
    private String skillLevel;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "status")
    private String status;

    @Column(name = "verified_by_id")
    private String verifiedById;

    @Column(name = "rejected_by_id")
    private String rejectedById;

    @Column(name = "submitted_at")
    private String submittedAt;

    @Column(name = "updated_at")
    private String updatedAt;
}