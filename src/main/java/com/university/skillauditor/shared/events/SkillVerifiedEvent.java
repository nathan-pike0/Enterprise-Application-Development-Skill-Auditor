package com.university.skillauditor.shared.events;

import java.time.LocalDate;

public record SkillVerifiedEvent(
        Long id,
        LocalDate occurredOn,
        String portfolioId,
        String skillId,
        String staffMemberId,
        String verifiedById
) implements LocalEvent {

    public SkillVerifiedEvent(LocalDate occurredOn, String portfolioId, String skillId,
                              String staffMemberId, String verifiedById) {
        this(null, occurredOn, portfolioId, skillId, staffMemberId, verifiedById);
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public SkillVerifiedEvent withId(Long newId) {
        return new SkillVerifiedEvent(newId, occurredOn, portfolioId, skillId, staffMemberId, verifiedById);
    }
}