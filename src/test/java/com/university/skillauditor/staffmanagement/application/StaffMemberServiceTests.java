package com.university.skillauditor.staffmanagement.application;

import com.university.skillauditor.shared.exceptions.StaffMemberNotFoundException;
import com.university.skillauditor.staffmanagement.api.StaffMemberResponse;
import com.university.skillauditor.staffmanagement.domain.EmploymentType;
import com.university.skillauditor.staffmanagement.infrastructure.StaffMemberEntity;
import com.university.skillauditor.staffmanagement.infrastructure.StaffMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffMemberServiceTests {

    @Mock
    private StaffMemberRepository staffMemberRepository;

    @InjectMocks
    private StaffMemberService staffMemberService;

    private StaffMemberEntity createValidEntity() {
        StaffMemberEntity entity = new StaffMemberEntity();
        entity.setId("staff-123");
        entity.setFirstName("John");
        entity.setLastName("Smith");
        entity.setEmail("john.smith@company.com");
        entity.setHireDate("2024-01-15");
        entity.setDepartmentId("dept-123");
        entity.setLineManagerId("manager-123");
        entity.setCurrentRole("Software Developer");
        entity.setRoleStartDate("2024-01-15");
        entity.setJobLevel("Junior");
        entity.setEmploymentType("FULL_TIME");
        entity.setEmploymentStatus("ACTIVE");
        return entity;
    }

    @Test
    @DisplayName("Creating a staff member saves an entity with the given id")
    void test01() {
        staffMemberService.createStaffMember(
                "staff-123", "John", "Smith", "john.smith@company.com",
                "2024-01-15", "dept-123", "manager-123", "Software Developer",
                "2024-01-15", "Junior", EmploymentType.FULL_TIME);

        ArgumentCaptor<StaffMemberEntity> captor = ArgumentCaptor.forClass(StaffMemberEntity.class);
        verify(staffMemberRepository).save(captor.capture());
        assertEquals("staff-123", captor.getValue().getId());
        assertEquals("ACTIVE", captor.getValue().getEmploymentStatus());
    }

    @Test
    @DisplayName("Getting a staff member by id returns the mapped response when found")
    void test02() {
        when(staffMemberRepository.findById("staff-123")).thenReturn(Optional.of(createValidEntity()));

        StaffMemberResponse response = staffMemberService.getStaffMemberById("staff-123");

        assertEquals("John", response.getFirstName());
    }

    @Test
    @DisplayName("Getting a staff member by id throws StaffMemberNotFoundException when not found")
    void test03() {
        when(staffMemberRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(StaffMemberNotFoundException.class, () ->
                staffMemberService.getStaffMemberById("nonexistent"));
    }

    @Test
    @DisplayName("Updating personal details saves the updated entity")
    void test04() {
        when(staffMemberRepository.findById("staff-123")).thenReturn(Optional.of(createValidEntity()));

        staffMemberService.updatePersonalDetails("staff-123", "Johnny", "Smith", "johnny@company.com");

        ArgumentCaptor<StaffMemberEntity> captor = ArgumentCaptor.forClass(StaffMemberEntity.class);
        verify(staffMemberRepository).save(captor.capture());
        assertEquals("Johnny", captor.getValue().getFirstName());
    }

    @Test
    @DisplayName("Updating personal details for a nonexistent staff member throws StaffMemberNotFoundException")
    void test05() {
        when(staffMemberRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(StaffMemberNotFoundException.class, () ->
                staffMemberService.updatePersonalDetails("nonexistent", "John", "Smith", "a@b.com"));
    }

    @Test
    @DisplayName("Updating department saves the updated entity")
    void test06() {
        when(staffMemberRepository.findById("staff-123")).thenReturn(Optional.of(createValidEntity()));

        staffMemberService.updateDepartment("staff-123", "dept-999");

        ArgumentCaptor<StaffMemberEntity> captor = ArgumentCaptor.forClass(StaffMemberEntity.class);
        verify(staffMemberRepository).save(captor.capture());
        assertEquals("dept-999", captor.getValue().getDepartmentId());
    }

    @Test
    @DisplayName("Updating placement saves the updated entity")
    void test07() {
        when(staffMemberRepository.findById("staff-123")).thenReturn(Optional.of(createValidEntity()));

        staffMemberService.updatePlacement("staff-123", "Senior Developer", "Senior", EmploymentType.FULL_TIME);

        ArgumentCaptor<StaffMemberEntity> captor = ArgumentCaptor.forClass(StaffMemberEntity.class);
        verify(staffMemberRepository).save(captor.capture());
        assertEquals("Senior Developer", captor.getValue().getCurrentRole());
    }

    @Test
    @DisplayName("Updating placement for a nonexistent staff member throws StaffMemberNotFoundException")
    void test08() {
        when(staffMemberRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(StaffMemberNotFoundException.class, () ->
                staffMemberService.updatePlacement("nonexistent", "Role", "Level", EmploymentType.FULL_TIME));
    }
}