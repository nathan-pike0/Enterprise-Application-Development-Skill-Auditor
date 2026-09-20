package com.university.skillauditor.staffmanagement.domain;

import com.university.skillauditor.shared.AggregateRoot;
import com.university.skillauditor.shared.Identity;
import java.time.LocalDate;
import static com.university.skillauditor.shared.DomainAssertions.argumentNotEmpty;

public class StaffMember extends AggregateRoot<StaffMember> {    private String firstName;
    private String lastName;
    private String email;
    private final LocalDate hireDate;
    private String departmentId;
    private final String lineManagerId;
    private String currentRole;
    private final LocalDate roleStartDate;
    private String jobLevel;
    private EmploymentType employmentType;
    private final EmploymentStatus employmentStatus;

    public static final String FIRST_NAME_CANNOT_BE_EMPTY = "First name cannot be empty";
    public static final String LAST_NAME_CANNOT_BE_EMPTY = "Last name cannot be empty";
    public static final String EMAIL_CANNOT_BE_EMPTY = "Email cannot be empty";
    public static final String DEPARTMENT_ID_CANNOT_BE_NULL = "Department ID cannot be null";
    public static final String CURRENT_ROLE_CANNOT_BE_EMPTY = "Current role cannot be empty";
    public static final String EMPLOYMENT_TYPE_CANNOT_BE_NULL = "Employment type cannot be null";

    public StaffMember(Identity<StaffMember> id,
                       String firstName,
                       String lastName,
                       String email,
                       LocalDate hireDate,
                       String departmentId,
                       String lineManagerId,
                       String currentRole,
                       LocalDate roleStartDate,
                       String jobLevel,
                       EmploymentType employmentType) {
        super(id);
        argumentNotEmpty(firstName, FIRST_NAME_CANNOT_BE_EMPTY);
        argumentNotEmpty(lastName, LAST_NAME_CANNOT_BE_EMPTY);
        argumentNotEmpty(email, EMAIL_CANNOT_BE_EMPTY);
        argumentNotEmpty(departmentId, DEPARTMENT_ID_CANNOT_BE_NULL);
        argumentNotEmpty(currentRole, CURRENT_ROLE_CANNOT_BE_EMPTY);
        if (employmentType == null) {
            throw new IllegalArgumentException(EMPLOYMENT_TYPE_CANNOT_BE_NULL);
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
        this.departmentId = departmentId;
        this.lineManagerId = lineManagerId;
        this.currentRole = currentRole;
        this.roleStartDate = roleStartDate;
        this.jobLevel = jobLevel;
        this.employmentType = employmentType;
        this.employmentStatus = EmploymentStatus.ACTIVE;
    }

    public final void updatePersonalDetails(String firstName, String lastName, String email) {
        argumentNotEmpty(firstName, FIRST_NAME_CANNOT_BE_EMPTY);
        argumentNotEmpty(lastName, LAST_NAME_CANNOT_BE_EMPTY);
        argumentNotEmpty(email, EMAIL_CANNOT_BE_EMPTY);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public final void updateDepartment(String departmentId) {
        argumentNotEmpty(departmentId, DEPARTMENT_ID_CANNOT_BE_NULL);
        this.departmentId = departmentId;
    }

    public final void updatePlacement(String currentRole, String jobLevel,
                                      EmploymentType employmentType) {
        argumentNotEmpty(currentRole, CURRENT_ROLE_CANNOT_BE_EMPTY);
        if (employmentType == null) {
            throw new IllegalArgumentException(EMPLOYMENT_TYPE_CANNOT_BE_NULL);
        }
        this.currentRole = currentRole;
        this.jobLevel = jobLevel;
        this.employmentType = employmentType;
    }

    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String email() { return email; }
    public LocalDate hireDate() { return hireDate; }
    public String departmentId() { return departmentId; }
    public String lineManagerId() { return lineManagerId; }
    public String currentRole() { return currentRole; }
    public LocalDate roleStartDate() { return roleStartDate; }
    public String jobLevel() { return jobLevel; }
    public EmploymentType employmentType() { return employmentType; }
    public EmploymentStatus employmentStatus() { return employmentStatus; }
}