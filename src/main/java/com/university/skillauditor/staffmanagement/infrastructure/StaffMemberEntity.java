package com.university.skillauditor.staffmanagement.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "staff_member")
@Getter
@Setter
public class StaffMemberEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "hire_date")
    private String hireDate;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "line_manager_id")
    private String lineManagerId;

    @Column(name = "job_role")
    private String currentRole;

    @Column(name = "role_start_date")
    private String roleStartDate;

    @Column(name = "job_level")
    private String jobLevel;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "employment_status")
    private String employmentStatus;
}