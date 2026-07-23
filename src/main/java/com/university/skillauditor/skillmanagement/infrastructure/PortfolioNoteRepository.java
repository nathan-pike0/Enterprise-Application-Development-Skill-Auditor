package com.university.skillauditor.skillmanagement.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioNoteRepository
        extends CrudRepository<PortfolioNoteEntity, String> {
}