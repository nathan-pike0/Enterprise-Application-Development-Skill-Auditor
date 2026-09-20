package com.university.skillauditor.staffmanagement.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StaffMemberResponse {
    String id;
    String firstName;
    String lastName;
    String email;
    String hireDate;
    String departmentId;
    String lineManagerId;
    String currentRole;
    String roleStartDate;
    String jobLevel;
    String employmentType;
    String employmentStatus;
}