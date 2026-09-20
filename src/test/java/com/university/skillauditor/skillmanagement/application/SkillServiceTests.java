package com.university.skillauditor.skillmanagement.application;

import com.university.skillauditor.shared.exceptions.SkillNotFoundException;
import com.university.skillauditor.skillmanagement.api.SkillResponse;
import com.university.skillauditor.skillmanagement.infrastructure.SkillEntity;
import com.university.skillauditor.skillmanagement.infrastructure.SkillRepository;
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
public class SkillServiceTests {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    private SkillEntity createValidEntity() {
        SkillEntity entity = new SkillEntity();
        entity.setId("skill-123");
        entity.setName("Java Programming");
        entity.setDescription("Core Java skills");
        entity.setCategory("Software Development");
        entity.setStatus("ACTIVE");
        return entity;
    }

    @Test
    @DisplayName("Creating a skill saves an entity to the repository")
    void test01() {
        skillService.createSkill("Java Programming", "Core Java skills", "Software Development");

        ArgumentCaptor<SkillEntity> captor = ArgumentCaptor.forClass(SkillEntity.class);
        verify(skillRepository).save(captor.capture());

        SkillEntity saved = captor.getValue();
        assertEquals("Java Programming", saved.getName());
        assertEquals("ACTIVE", saved.getStatus());
    }

    @Test
    @DisplayName("Getting a skill by id returns the mapped response when found")
    void test02() {
        when(skillRepository.findById("skill-123")).thenReturn(Optional.of(createValidEntity()));

        SkillResponse response = skillService.getSkillById("skill-123");

        assertEquals("Java Programming", response.getName());
    }

    @Test
    @DisplayName("Getting a skill by id throws SkillNotFoundException when not found")
    void test03() {
        when(skillRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(SkillNotFoundException.class, () -> skillService.getSkillById("nonexistent"));
    }

    @Test
    @DisplayName("Updating a skill saves the updated entity")
    void test04() {
        when(skillRepository.findById("skill-123")).thenReturn(Optional.of(createValidEntity()));

        skillService.updateSkill("skill-123", "New Name", "New Description", "New Category");

        ArgumentCaptor<SkillEntity> captor = ArgumentCaptor.forClass(SkillEntity.class);
        verify(skillRepository).save(captor.capture());
        assertEquals("New Name", captor.getValue().getName());
    }

    @Test
    @DisplayName("Updating a nonexistent skill throws SkillNotFoundException")
    void test05() {
        when(skillRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(SkillNotFoundException.class, () ->
                skillService.updateSkill("nonexistent", "name", "description", "category"));
    }

    @Test
    @DisplayName("Deactivating an active skill sets its status to INACTIVE")
    void test06() {
        when(skillRepository.findById("skill-123")).thenReturn(Optional.of(createValidEntity()));

        skillService.deactivateSkill("skill-123");

        ArgumentCaptor<SkillEntity> captor = ArgumentCaptor.forClass(SkillEntity.class);
        verify(skillRepository).save(captor.capture());
        assertEquals("INACTIVE", captor.getValue().getStatus());
    }

    @Test
    @DisplayName("Deactivating an already inactive skill throws IllegalArgumentException")
    void test07() {
        SkillEntity inactive = createValidEntity();
        inactive.setStatus("INACTIVE");
        when(skillRepository.findById("skill-123")).thenReturn(Optional.of(inactive));
        assertThrows(IllegalArgumentException.class, () -> skillService.deactivateSkill("skill-123"));
    }

    @Test
    @DisplayName("Activating an inactive skill sets its status to ACTIVE")
    void test08() {
        SkillEntity inactive = createValidEntity();
        inactive.setStatus("INACTIVE");
        when(skillRepository.findById("skill-123")).thenReturn(Optional.of(inactive));

        skillService.activateSkill("skill-123");

        ArgumentCaptor<SkillEntity> captor = ArgumentCaptor.forClass(SkillEntity.class);
        verify(skillRepository).save(captor.capture());
        assertEquals("ACTIVE", captor.getValue().getStatus());
    }
}