package com.university.skillauditor.staffmanagement.application;

import com.university.skillauditor.shared.Identity;
import com.university.skillauditor.shared.exceptions.StaffMemberNotFoundException;
import com.university.skillauditor.staffmanagement.api.StaffMemberResponse;
import com.university.skillauditor.staffmanagement.domain.EmploymentType;
import com.university.skillauditor.staffmanagement.domain.StaffMember;
import com.university.skillauditor.staffmanagement.infrastructure.StaffMemberEntity;
import com.university.skillauditor.staffmanagement.infrastructure.StaffMemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class StaffMemberService {

    private StaffMemberRepository staffMemberRepository;

    public void createStaffMember(String staffMemberId, String firstName, String lastName, String email,
                                  String hireDate, String departmentId, String lineManagerId,
                                  String currentRole, String roleStartDate, String jobLevel,
                                  EmploymentType employmentType) {
        log.info("Creating staff member: id={}, name={} {}", staffMemberId, firstName, lastName);

        Identity<StaffMember> id = Identity.of(staffMemberId);
        StaffMember staffMember = new StaffMember(
                id, firstName, lastName, email,
                hireDate != null ? LocalDate.parse(hireDate) : null,
                departmentId, lineManagerId, currentRole,
                roleStartDate != null ? LocalDate.parse(roleStartDate) : null,
                jobLevel, employmentType
        );
        StaffMemberEntity entity = new StaffMemberEntity();
        entity.setId(staffMember.id().id());
        entity.setFirstName(staffMember.firstName());
        entity.setLastName(staffMember.lastName());
        entity.setEmail(staffMember.email());
        entity.setHireDate(hireDate);
        entity.setDepartmentId(staffMember.departmentId());
        entity.setLineManagerId(staffMember.lineManagerId());
        entity.setCurrentRole(staffMember.currentRole());
        entity.setRoleStartDate(roleStartDate);
        entity.setJobLevel(staffMember.jobLevel());
        entity.setEmploymentType(staffMember.employmentType().name());
        entity.setEmploymentStatus(staffMember.employmentStatus().name());
        staffMemberRepository.save(entity);
    }

    public List<StaffMemberResponse> getAllStaffMembers() {
        Iterable<StaffMemberEntity> entities = staffMemberRepository.findAll();
        List<StaffMemberResponse> responses = new ArrayList<>();
        for (StaffMemberEntity entity : entities) {
            responses.add(toResponse(entity));
        }
        return responses;
    }

    public StaffMemberResponse getStaffMemberById(String id) {
        Optional<StaffMemberEntity> result = staffMemberRepository.findById(id);
        if (result.isEmpty()) {
            throw new StaffMemberNotFoundException(id);
        }
        return toResponse(result.get());
    }

    public void updatePersonalDetails(String id, String firstName,
                                      String lastName, String email) {
        log.info("Updating personal details for staff: id={}", id);

        Optional<StaffMemberEntity> result = staffMemberRepository.findById(id);
        if (result.isEmpty()) {
            throw new StaffMemberNotFoundException(id);
        }
        StaffMemberEntity entity = result.get();
        StaffMember staffMember = toDomain(entity);
        staffMember.updatePersonalDetails(firstName, lastName, email);
        entity.setFirstName(staffMember.firstName());
        entity.setLastName(staffMember.lastName());
        entity.setEmail(staffMember.email());
        staffMemberRepository.save(entity);
    }

    public void updateDepartment(String id, String departmentId) {
        log.info("Updating department for staff: id={}, departmentId={}", id, departmentId);

        Optional<StaffMemberEntity> result = staffMemberRepository.findById(id);
        if (result.isEmpty()) {
            throw new StaffMemberNotFoundException(id);
        }
        StaffMemberEntity entity = result.get();
        StaffMember staffMember = toDomain(entity);
        staffMember.updateDepartment(departmentId);
        entity.setDepartmentId(staffMember.departmentId());
        staffMemberRepository.save(entity);
    }

    public void updatePlacement(String id, String currentRole,
                                String jobLevel, EmploymentType employmentType) {
        log.info("Updating placement for staff: id={}, role={}", id, currentRole);

        Optional<StaffMemberEntity> result = staffMemberRepository.findById(id);
        if (result.isEmpty()) {
            throw new StaffMemberNotFoundException(id);
        }
        StaffMemberEntity entity = result.get();
        StaffMember staffMember = toDomain(entity);
        staffMember.updatePlacement(currentRole, jobLevel, employmentType);
        entity.setCurrentRole(staffMember.currentRole());
        entity.setJobLevel(staffMember.jobLevel());
        entity.setEmploymentType(staffMember.employmentType().name());
        staffMemberRepository.save(entity);
    }

    private StaffMember toDomain(StaffMemberEntity entity) {
        return new StaffMember(
                Identity.of(entity.getId()),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getHireDate() != null ? LocalDate.parse(entity.getHireDate()) : null,
                entity.getDepartmentId(),
                entity.getLineManagerId(),
                entity.getCurrentRole(),
                entity.getRoleStartDate() != null ? LocalDate.parse(entity.getRoleStartDate()) : null,
                entity.getJobLevel(),
                EmploymentType.valueOf(entity.getEmploymentType())
        );
    }

    private StaffMemberResponse toResponse(StaffMemberEntity entity) {
        return new StaffMemberResponse(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getHireDate(),
                entity.getDepartmentId(),
                entity.getLineManagerId(),
                entity.getCurrentRole(),
                entity.getRoleStartDate(),
                entity.getJobLevel(),
                entity.getEmploymentType(),
                entity.getEmploymentStatus()
        );
    }
}