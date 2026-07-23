package com.university.skillauditor.skillmanagement.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SkillPortfolioResponse {
    private String id;
    private String staffMemberId;
    private String skillId;
    private String skillLevel;
    private String expiryDate;
    private String status;
    private String verifiedById;
    private String rejectedById;
    private String submittedAt;
    private String updatedAt;
    private List<String> notes;
}