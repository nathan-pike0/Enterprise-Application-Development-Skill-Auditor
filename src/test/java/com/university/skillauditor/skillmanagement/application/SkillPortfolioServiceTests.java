package com.university.skillauditor.skillmanagement.application;

import com.university.skillauditor.shared.events.DomainEventManager;
import com.university.skillauditor.shared.exceptions.SkillPortfolioNotFoundException;
import com.university.skillauditor.skillmanagement.api.SkillPortfolioResponse;
import com.university.skillauditor.skillmanagement.domain.SkillLevel;
import com.university.skillauditor.skillmanagement.infrastructure.PortfolioNoteRepository;
import com.university.skillauditor.skillmanagement.infrastructure.SkillPortfolioEntity;
import com.university.skillauditor.skillmanagement.infrastructure.SkillPortfolioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillPortfolioServiceTests {

    @Mock
    private SkillPortfolioRepository skillPortfolioRepository;
    @Mock
    private PortfolioNoteRepository portfolioNoteRepository;
    @Mock
    private DomainEventManager domainEventManager;
    @InjectMocks
    private SkillPortfolioService skillPortfolioService;

    private SkillPortfolioEntity createPendingEntity() {
        SkillPortfolioEntity entity = new SkillPortfolioEntity();
        entity.setId("portfolio-123");
        entity.setStaffMemberId("staff-123");
        entity.setSkillId("skill-123");
        entity.setSkillLevel(SkillLevel.BEGINNER.name());
        entity.setStatus("PENDING");
        entity.setSubmittedAt(LocalDateTime.now().toString());
        entity.setUpdatedAt(LocalDateTime.now().toString());
        return entity;
    }

    @Test
    @DisplayName("Creating a portfolio entry saves an entity with PENDING status")
    void test01() {
        skillPortfolioService.createPortfolioEntry("staff-123", "skill-123", SkillLevel.BEGINNER, null);

        ArgumentCaptor<SkillPortfolioEntity> captor = ArgumentCaptor.forClass(SkillPortfolioEntity.class);
        verify(skillPortfolioRepository).save(captor.capture());
        assertEquals("PENDING", captor.getValue().getStatus());
    }

    @Test
    @DisplayName("Verifying a pending entry sets status to VERIFIED and records the manager")
    void test02() {
        when(skillPortfolioRepository.findById("portfolio-123")).thenReturn(Optional.of(createPendingEntity()));

        skillPortfolioService.verifyEntry("portfolio-123", "manager-123");

        ArgumentCaptor<SkillPortfolioEntity> captor = ArgumentCaptor.forClass(SkillPortfolioEntity.class);
        verify(skillPortfolioRepository).save(captor.capture());
        assertEquals("VERIFIED", captor.getValue().getStatus());
        assertEquals("manager-123", captor.getValue().getVerifiedById());
    }

    @Test
    @DisplayName("Verifying an already verified entry throws IllegalArgumentException")
    void test03() {
        SkillPortfolioEntity verified = createPendingEntity();
        verified.setStatus("VERIFIED");
        when(skillPortfolioRepository.findById("portfolio-123")).thenReturn(Optional.of(verified));

        assertThrows(IllegalArgumentException.class,
                () -> skillPortfolioService.verifyEntry("portfolio-123", "manager-123"));
    }

    @Test
    @DisplayName("Rejecting a pending entry sets status to REJECTED")
    void test04() {
        when(skillPortfolioRepository.findById("portfolio-123")).thenReturn(Optional.of(createPendingEntity()));

        skillPortfolioService.rejectEntry("portfolio-123", "manager-123");

        ArgumentCaptor<SkillPortfolioEntity> captor = ArgumentCaptor.forClass(SkillPortfolioEntity.class);
        verify(skillPortfolioRepository).save(captor.capture());
        assertEquals("REJECTED", captor.getValue().getStatus());
    }

    @Test
    @DisplayName("Getting a nonexistent portfolio entry throws SkillPortfolioNotFoundException")
    void test05() {
        when(skillPortfolioRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(SkillPortfolioNotFoundException.class, () ->
                skillPortfolioService.getEntryById("nonexistent"));
    }

    @Test
    @DisplayName("Getting an entry by id includes its notes")
    void test06() {
        when(skillPortfolioRepository.findById("portfolio-123")).thenReturn(Optional.of(createPendingEntity()));
        when(portfolioNoteRepository.findAll()).thenReturn(new ArrayList<>());

        SkillPortfolioResponse response = skillPortfolioService.getEntryById("portfolio-123");

        assertNotNull(response.getNotes());
        assertTrue(response.getNotes().isEmpty());
    }


    @Test
    @DisplayName("Adding a note to a nonexistent entry throws SkillPortfolioNotFoundException")
    void test07() {
        when(skillPortfolioRepository.findById("nonexistent")).thenReturn(Optional.empty());
        assertThrows(SkillPortfolioNotFoundException.class, () ->
                skillPortfolioService.addNote("nonexistent", "a note", "manager-123"));
    }
}