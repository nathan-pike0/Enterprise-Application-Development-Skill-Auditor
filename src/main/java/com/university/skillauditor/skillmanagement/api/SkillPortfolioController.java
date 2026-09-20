package com.university.skillauditor.skillmanagement.api;

import com.university.skillauditor.skillmanagement.SkillManagementFacade;
import com.university.skillauditor.skillmanagement.domain.SkillLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@AllArgsConstructor
public class SkillPortfolioController {

    private SkillManagementFacade skillManagementFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPortfolioEntry(@RequestBody SkillPortfolioRequest request, Authentication authentication) {
        skillManagementFacade.createPortfolioEntry(
                authentication.getName(),
                request.getSkillId(),
                SkillLevel.valueOf(request.getSkillLevel()),
                request.getExpiryDate() != null ? LocalDate.parse(request.getExpiryDate()) : null
        );
    }

    @PatchMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editPortfolioEntry(@PathVariable String id,
                                   @RequestBody SkillPortfolioRequest request,
                                   Authentication authentication) {
        skillManagementFacade.editPortfolioEntry(
                id,
                authentication.getName(),
                SkillLevel.valueOf(request.getSkillLevel()),
                request.getExpiryDate() != null ? LocalDate.parse(request.getExpiryDate()) : null
        );
    }
    @GetMapping
    public List<SkillPortfolioResponse> getAllEntries(
            @RequestParam(required = false) String staffMemberId,
            @RequestParam(required = false) String skillId,
            @RequestParam(required = false) String skillLevel) {
        return skillManagementFacade.getFilteredEntries(staffMemberId, skillId, skillLevel);
    }

    @GetMapping("/my")
    public List<SkillPortfolioResponse> getMyEntries(Authentication authentication) {
        return skillManagementFacade.getMyEntries(authentication.getName());
    }

    @GetMapping("/{id}")
    public SkillPortfolioResponse getEntryById(@PathVariable String id) {
        return skillManagementFacade.getEntryById(id);
    }

    @GetMapping("/pending")
    public List<SkillPortfolioResponse> getPendingEntries() {
        return skillManagementFacade.getPendingEntries();
    }

    @GetMapping("/expired")
    public List<SkillPortfolioResponse> getExpiredEntries() {
        return skillManagementFacade.getExpiredEntries();
    }

    @PatchMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyEntry(@PathVariable String id, Authentication authentication) {
        skillManagementFacade.verifyEntry(id, authentication.getName());
    }

    @PatchMapping("/{id}/unverify")
    @ResponseStatus(HttpStatus.OK)
    public void unverifyEntry(@PathVariable String id) {
        skillManagementFacade.unverifyEntry(id);
    }

    @PatchMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    public void rejectEntry(@PathVariable String id, Authentication authentication) {
        skillManagementFacade.rejectEntry(id, authentication.getName());
    }

    @PostMapping("/{id}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNote(@PathVariable String id,
                        @RequestBody AddNoteRequest request, Authentication authentication) {
        skillManagementFacade.addNote(id, request.getNote(), authentication.getName());
    }
}