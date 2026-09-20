package com.university.skillauditor.staffmanagement.api;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StaffMemberRequest {
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
}