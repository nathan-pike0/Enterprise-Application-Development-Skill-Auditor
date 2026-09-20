package com.university.skillauditor.skillmanagement;

import com.university.skillauditor.skillmanagement.api.SkillPortfolioResponse;
import com.university.skillauditor.skillmanagement.api.SkillResponse;
import com.university.skillauditor.skillmanagement.application.SkillPortfolioService;
import com.university.skillauditor.skillmanagement.application.SkillService;
import com.university.skillauditor.skillmanagement.domain.SkillLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class SkillManagementFacade {

    private final SkillService skillService;
    private final SkillPortfolioService skillPortfolioService;

    @PreAuthorize("hasRole('SKILL_MANAGER')")
    public void createSkill(String name, String description, String category) {
        skillService.createSkill(name, description, category);
    }

    @PreAuthorize("hasAnyRole('STAFF','MANAGER','SKILL_MANAGER','ADMIN')")
    public Iterable<SkillResponse> getAllSkills() {
        return skillService.getAllSkills();
    }

    @PreAuthorize("hasAnyRole('STAFF','MANAGER','SKILL_MANAGER','ADMIN')")
    public SkillResponse getSkillById(String id) {
        return skillService.getSkillById(id);
    }

    @PreAuthorize("hasRole('SKILL_MANAGER')")
    public void updateSkill(String id, String name, String description, String category) {
        skillService.updateSkill(id, name, description, category);
    }

    @PreAuthorize("hasRole('SKILL_MANAGER')")
    public void activateSkill(String id) {
        skillService.activateSkill(id);
    }

    @PreAuthorize("hasRole('SKILL_MANAGER')")
    public void deactivateSkill(String id) {
        skillService.deactivateSkill(id);
    }

    @PreAuthorize("hasRole('STAFF')")
    public void createPortfolioEntry(String staffMemberId, String skillId, SkillLevel skillLevel, LocalDate expiryDate) {
        skillPortfolioService.createPortfolioEntry(staffMemberId, skillId, skillLevel, expiryDate);
    }

    @PreAuthorize("hasRole('STAFF')")
    public List<SkillPortfolioResponse> getMyEntries(String staffMemberId) {
        return skillPortfolioService.getEntriesByStaffMemberId(staffMemberId);
    }

    @PreAuthorize("hasRole('STAFF')")
    public void editPortfolioEntry(String id, String staffMemberId, SkillLevel skillLevel, LocalDate expiryDate) {
        skillPortfolioService.editPortfolioEntry(id, staffMemberId, skillLevel, expiryDate);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SKILL_MANAGER','ADMIN')")
    public List<SkillPortfolioResponse> getAllEntries() {
        return skillPortfolioService.getAllEntries();
    }

    @PreAuthorize("hasRole('MANAGER')")
    public List<SkillPortfolioResponse> getPendingEntries() {
        return skillPortfolioService.getPendingEntries();
    }

    @PreAuthorize("hasAnyRole('MANAGER','SKILL_MANAGER')")
    public List<SkillPortfolioResponse> getExpiredEntries() {
        return skillPortfolioService.getExpiredEntries();
    }

    @PreAuthorize("hasAnyRole('STAFF','MANAGER','SKILL_MANAGER','ADMIN')")
    public SkillPortfolioResponse getEntryById(String id) {
        return skillPortfolioService.getEntryById(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public void verifyEntry(String id, String managerId) {
        skillPortfolioService.verifyEntry(id, managerId);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public void unverifyEntry(String id) {
        skillPortfolioService.unverifyEntry(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public void rejectEntry(String id, String managerId) {
        skillPortfolioService.rejectEntry(id, managerId);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public void addNote(String id, String note, String managerId) {
        skillPortfolioService.addNote(id, note, managerId);
    }

    @PreAuthorize("hasAnyRole('MANAGER','SKILL_MANAGER','ADMIN')")
    public List<SkillPortfolioResponse> getFilteredEntries(String staffMemberId, String skillId, String skillLevel) {
        return skillPortfolioService.getFilteredEntries(staffMemberId, skillId, skillLevel);
    }
}