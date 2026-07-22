package com.university.skillauditor.skillmanagement.domain;

import com.university.skillauditor.shared.Entity;
import com.university.skillauditor.shared.Identity;
import static com.university.skillauditor.shared.DomainAssertions.argumentNotEmpty;
import java.time.LocalDateTime;

public class PortfolioNote extends Entity<PortfolioNote> {

    private String portfolioId;
    private String note;
    private String addedById;
    private LocalDateTime addedAt;

    public static final String NOTE_CANNOT_BE_EMPTY = "Note cannot be empty";
    public static final String ADDED_BY_CANNOT_BE_NULL = "Added by ID cannot be null";
    public static final String PORTFOLIO_ID_CANNOT_BE_NULL = "Portfolio ID cannot be null";

    public PortfolioNote(Identity<PortfolioNote> id,
                         String portfolioId,
                         String note,
                         String addedById) {
        super(id);
        argumentNotEmpty(portfolioId, PORTFOLIO_ID_CANNOT_BE_NULL);
        argumentNotEmpty(note, NOTE_CANNOT_BE_EMPTY);
        argumentNotEmpty(addedById, ADDED_BY_CANNOT_BE_NULL);
        this.portfolioId = portfolioId;
        this.note = note;
        this.addedById = addedById;
        this.addedAt = LocalDateTime.now();
    }

    public String portfolioId() { return portfolioId; }
    public String note() { return note; }
    public String addedById() { return addedById; }
    public LocalDateTime addedAt() { return addedAt; }
}