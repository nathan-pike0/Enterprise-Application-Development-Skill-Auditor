package com.university.skillauditor.skillmanagement.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillPortfolioRequest {
    private String staffMemberId;
    private String skillId;
    private String skillLevel;
    private String expiryDate;
}