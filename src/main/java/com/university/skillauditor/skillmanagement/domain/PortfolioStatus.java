package com.university.skillauditor.skillmanagement.domain;

public enum PortfolioStatus {
    PENDING("Awaiting Verification"),
    VERIFIED("Accepted by Manager"),
    REJECTED("Rejected by Manager"),
    EXPIRED("Skill Outdated");


    private final String description;

    PortfolioStatus(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public String description() {
        return description;
    }
}
