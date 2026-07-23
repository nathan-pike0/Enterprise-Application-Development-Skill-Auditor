package com.university.skillauditor.skillmanagement.application;

import com.university.skillauditor.shared.Identity;
import com.university.skillauditor.skillmanagement.api.SkillPortfolioResponse;
import com.university.skillauditor.skillmanagement.domain.PortfolioNote;
import com.university.skillauditor.skillmanagement.domain.PortfolioStatus;
import com.university.skillauditor.skillmanagement.domain.SkillLevel;
import com.university.skillauditor.skillmanagement.domain.SkillPortfolio;
import com.university.skillauditor.skillmanagement.infrastructure.PortfolioNoteEntity;
import com.university.skillauditor.skillmanagement.infrastructure.PortfolioNoteRepository;
import com.university.skillauditor.skillmanagement.infrastructure.SkillPortfolioEntity;
import com.university.skillauditor.skillmanagement.infrastructure.SkillPortfolioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SkillPortfolioService {

    private SkillPortfolioRepository skillPortfolioRepository;
    private PortfolioNoteRepository portfolioNoteRepository;

    public void createPortfolioEntry(String staffMemberId, String skillId,
                                     SkillLevel skillLevel, LocalDate expiryDate) {
        Identity<SkillPortfolio> id = Identity.generateId();
        SkillPortfolio portfolio = new SkillPortfolio(id, staffMemberId, skillId, skillLevel, expiryDate);

        SkillPortfolioEntity entity = new SkillPortfolioEntity();
        entity.setId(portfolio.id().id());
        entity.setStaffMemberId(portfolio.staffMemberId());
        entity.setSkillId(portfolio.skillId());
        entity.setSkillLevel(portfolio.skillLevel().name());
        entity.setExpiryDate(portfolio.expiryDate() != null ? portfolio.expiryDate().toString() : null);
        entity.setStatus(portfolio.status().name());
        entity.setVerifiedById(null);
        entity.setRejectedById(null);
        entity.setSubmittedAt(portfolio.submittedAt().toString());
        entity.setUpdatedAt(portfolio.updatedAt().toString());
        skillPortfolioRepository.save(entity);
    }

    public void editPortfolioEntry(String id, SkillLevel skillLevel, LocalDate expiryDate) {
        Optional<SkillPortfolioEntity> result = skillPortfolioRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio entry not found");
        }
        SkillPortfolioEntity entity = result.get();
        SkillPortfolio portfolio = toDomain(entity);
        portfolio.editSkill(skillLevel, expiryDate);
        entity.setSkillLevel(portfolio.skillLevel().name());
        entity.setExpiryDate(portfolio.expiryDate() != null ? portfolio.expiryDate().toString() : null);
        entity.setStatus(portfolio.status().name());
        entity.setUpdatedAt(LocalDateTime.now().toString());
        skillPortfolioRepository.save(entity);
    }

    public void verifyEntry(String id, String managerId) {
        Optional<SkillPortfolioEntity> result = skillPortfolioRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio entry not found");
        }
        SkillPortfolioEntity entity = result.get();
        SkillPortfolio portfolio = toDomain(entity);
        portfolio.verifySkill(managerId);
        entity.setStatus(portfolio.status().name());
        entity.setVerifiedById(managerId);
        entity.setUpdatedAt(LocalDateTime.now().toString());
        skillPortfolioRepository.save(entity);
    }

    public void unverifyEntry(String id) {
        Optional<SkillPortfolioEntity> result = skillPortfolioRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio entry not found");
        }
        SkillPortfolioEntity entity = result.get();
        SkillPortfolio portfolio = toDomain(entity);
        portfolio.unverifySkill();
        entity.setStatus(portfolio.status().name());
        entity.setVerifiedById(null);
        entity.setUpdatedAt(LocalDateTime.now().toString());
        skillPortfolioRepository.save(entity);
    }

    public void rejectEntry(String id, String managerId) {
        Optional<SkillPortfolioEntity> result = skillPortfolioRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio entry not found");
        }
        SkillPortfolioEntity entity = result.get();
        SkillPortfolio portfolio = toDomain(entity);
        portfolio.rejectSkill(managerId);
        entity.setStatus(portfolio.status().name());
        entity.setRejectedById(managerId);
        entity.setUpdatedAt(LocalDateTime.now().toString());
        skillPortfolioRepository.save(entity);
    }

    public void addNote(String id, String note, String managerId) {
        Optional<SkillPortfolioEntity> result = skillPortfolioRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio entry not found");
        }
        PortfolioNoteEntity noteEntity = new PortfolioNoteEntity();
        noteEntity.setId(Identity.generateId().id());
        noteEntity.setPortfolioId(id);
        noteEntity.setNote(note);
        noteEntity.setAddedById(managerId);
        noteEntity.setAddedAt(LocalDateTime.now().toString());
        portfolioNoteRepository.save(noteEntity);
    }

    public List<SkillPortfolioResponse> getAllEntries() {
        Iterable<SkillPortfolioEntity> entities = skillPortfolioRepository.findAll();
        List<SkillPortfolioResponse> responses = new ArrayList<>();
        for (SkillPortfolioEntity entity : entities) {
            responses.add(toResponse(entity));
        }
        return responses;
    }

    public List<SkillPortfolioResponse> getPendingEntries() {
        Iterable<SkillPortfolioEntity> entities = skillPortfolioRepository.findAll();
        List<SkillPortfolioResponse> responses = new ArrayList<>();
        for (SkillPortfolioEntity entity : entities) {
            if (entity.getStatus().equals(PortfolioStatus.PENDING.name())) {
                responses.add(toResponse(entity));
            }
        }
        return responses;
    }

    public List<SkillPortfolioResponse> getExpiredEntries() {
        Iterable<SkillPortfolioEntity> entities = skillPortfolioRepository.findAll();
        List<SkillPortfolioResponse> responses = new ArrayList<>();
        for (SkillPortfolioEntity entity : entities) {
            if (entity.getExpiryDate() != null &&
                    LocalDate.parse(entity.getExpiryDate()).isBefore(LocalDate.now())) {
                responses.add(toResponse(entity));
            }
        }
        return responses;
    }

    public SkillPortfolioResponse getEntryById(String id) {
        Optional<SkillPortfolioEntity> result = skillPortfolioRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio entry not found");
        }
        return toResponse(result.get());
    }

    private SkillPortfolio toDomain(SkillPortfolioEntity entity) {
        return new SkillPortfolio(
                Identity.of(entity.getId()),
                entity.getStaffMemberId(),
                entity.getSkillId(),
                SkillLevel.valueOf(entity.getSkillLevel()),
                entity.getExpiryDate() != null ? LocalDate.parse(entity.getExpiryDate()) : null
        );
    }

    private SkillPortfolioResponse toResponse(SkillPortfolioEntity entity) {
        Iterable<PortfolioNoteEntity> noteEntities = portfolioNoteRepository.findAll();
        List<String> notes = new ArrayList<>();
        for (PortfolioNoteEntity note : noteEntities) {
            if (note.getPortfolioId().equals(entity.getId())) {
                notes.add(note.getNote());
            }
        }
        return new SkillPortfolioResponse(
                entity.getId(),
                entity.getStaffMemberId(),
                entity.getSkillId(),
                entity.getSkillLevel(),
                entity.getExpiryDate(),
                entity.getStatus(),
                entity.getVerifiedById(),
                entity.getRejectedById(),
                entity.getSubmittedAt(),
                entity.getUpdatedAt(),
                notes
        );
    }}