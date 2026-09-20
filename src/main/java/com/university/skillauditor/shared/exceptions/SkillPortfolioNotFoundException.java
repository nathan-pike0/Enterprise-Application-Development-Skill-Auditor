package com.university.skillauditor.shared.exceptions;

public class SkillPortfolioNotFoundException extends RuntimeException {
    public SkillPortfolioNotFoundException(String portfolioId) {
        super("Skill portfolio entry not found: " + portfolioId);
    }
}