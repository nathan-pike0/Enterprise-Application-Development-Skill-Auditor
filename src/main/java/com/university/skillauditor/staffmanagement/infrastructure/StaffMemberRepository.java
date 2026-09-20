package com.university.skillauditor.staffmanagement.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffMemberRepository
        extends CrudRepository<StaffMemberEntity, String> {
}