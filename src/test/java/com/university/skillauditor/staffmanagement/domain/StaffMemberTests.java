package com.university.skillauditor.staffmanagement.domain;

import com.university.skillauditor.shared.Identity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class StaffMemberTests {

    private StaffMember createValidStaffMember() {
        return new StaffMember(
                Identity.generateId(),
                "Jake",
                "Brandon",
                "jake@x.com",
                LocalDate.of(2024, 1, 15),
                "dept-123",
                "manager-123",
                "Software Developer",
                LocalDate.of(2024, 1, 15),
                "Junior",
                EmploymentType.FULL_TIME
        );
    }

    @Test
    @DisplayName("A valid staff member can be created")
    void test01() {
        assertDoesNotThrow(this::createValidStaffMember);
    }

    @Test
    @DisplayName("A new staff member always starts with ACTIVE employment status")
    void test02() {
        StaffMember staffMember = createValidStaffMember();
        assertEquals(EmploymentStatus.ACTIVE, staffMember.employmentStatus());
    }

    @Test
    @DisplayName("Cannot create a staff member with a blank first name")
    void test03() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new StaffMember(Identity.generateId(), "", "Smith", "a@b.com",
                        LocalDate.now(), "dept-1", "manager-1", "Role",
                        LocalDate.now(), "Junior", EmploymentType.FULL_TIME)
        );
        assertEquals(StaffMember.FIRST_NAME_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a staff member with a blank last name")
    void test04() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new StaffMember(Identity.generateId(), "John", "", "a@b.com",
                        LocalDate.now(), "dept-1", "manager-1", "Role",
                        LocalDate.now(), "Junior", EmploymentType.FULL_TIME)
        );
        assertEquals(StaffMember.LAST_NAME_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a staff member with a blank email")
    void test05() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new StaffMember(Identity.generateId(), "John", "Smith", "",
                        LocalDate.now(), "dept-1", "manager-1", "Role",
                        LocalDate.now(), "Junior", EmploymentType.FULL_TIME)
        );

        assertEquals(StaffMember.EMAIL_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a staff member with a blank department id")
    void test06() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new StaffMember(Identity.generateId(), "John", "Smith", "a@b.com",
                        LocalDate.now(), "", "manager-1", "Role",
                        LocalDate.now(), "Junior", EmploymentType.FULL_TIME)
        );
        assertEquals(StaffMember.DEPARTMENT_ID_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a staff member with a blank current role")
    void test07() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new StaffMember(Identity.generateId(), "John", "Smith", "a@b.com",
                        LocalDate.now(), "dept-1", "manager-1", "",
                        LocalDate.now(), "Junior", EmploymentType.FULL_TIME)
        );
        assertEquals(StaffMember.CURRENT_ROLE_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Cannot create a staff member with a null employment type")
    void test08() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new StaffMember(Identity.generateId(), "John", "Smith", "a@b.com",
                        LocalDate.now(), "dept-1", "manager-1", "Role",
                        LocalDate.now(), "Junior", null)
        );
        assertEquals(StaffMember.EMPLOYMENT_TYPE_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Can update personal details with valid values")
    void test09() {
        StaffMember staffMember = createValidStaffMember();
        assertDoesNotThrow(() -> staffMember.updatePersonalDetails("Johnny", "Smith", "johnny@company.com"));
    }

    @Test
    @DisplayName("Updating personal details actually changes the fields")
    void test10() {
        StaffMember staffMember = createValidStaffMember();
        staffMember.updatePersonalDetails("Johnny", "Smith", "johnny@company.com");
        assertEquals("Johnny", staffMember.firstName());
        assertEquals("johnny@company.com", staffMember.email());
    }

    @Test
    @DisplayName("Cannot update personal details with a blank first name")
    void test11() {
        StaffMember staffMember = createValidStaffMember();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                staffMember.updatePersonalDetails("", "Smith", "a@b.com")
        );
        assertEquals(StaffMember.FIRST_NAME_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Can update department with a valid value")
    void test12() {
        StaffMember staffMember = createValidStaffMember();
        staffMember.updateDepartment("dept-999");
        assertEquals("dept-999", staffMember.departmentId());
    }

    @Test
    @DisplayName("Cannot update department to a blank value")
    void test13() {
        StaffMember staffMember = createValidStaffMember();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                staffMember.updateDepartment("")
        );
        assertEquals(StaffMember.DEPARTMENT_ID_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Can update placement with valid values")
    void test14() {
        StaffMember staffMember = createValidStaffMember();
        staffMember.updatePlacement("Senior Developer", "Senior", EmploymentType.FULL_TIME);
        assertEquals("Senior Developer", staffMember.currentRole());
        assertEquals("Senior", staffMember.jobLevel());
    }

    @Test
    @DisplayName("Cannot update placement with a null employment type")
    void test15() {
        StaffMember staffMember = createValidStaffMember();
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                staffMember.updatePlacement("Role", "Level", null)
        );
        assertEquals(StaffMember.EMPLOYMENT_TYPE_CANNOT_BE_NULL, exception.getMessage());
    }
}