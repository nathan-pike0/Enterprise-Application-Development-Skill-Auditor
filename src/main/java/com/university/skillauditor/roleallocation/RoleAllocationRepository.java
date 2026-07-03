package com.university.skillauditor.roleallocation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAllocationRepository
        extends CrudRepository<RoleAllocation, Long> {
}