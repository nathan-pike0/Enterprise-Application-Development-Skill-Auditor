package com.university.skillauditor.skillmanagement.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "portfolio_note")
@Getter
@Setter
public class PortfolioNoteEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "portfolio_id")
    private String portfolioId;

    @Column(name = "note")
    private String note;

    @Column(name = "added_by_id")
    private String addedById;

    @Column(name = "added_at")
    private String addedAt;
}