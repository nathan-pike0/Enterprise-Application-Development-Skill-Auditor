package com.university.skillauditor.skillmanagement.infrastructure;

import com.university.skillauditor.skillmanagement.infrastructure.SkillEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository
        extends CrudRepository<SkillEntity, String> {
}