package com.university.skillauditor.skillmanagement.api;

import com.university.skillauditor.skillmanagement.application.SkillPortfolioService;
import com.university.skillauditor.skillmanagement.domain.SkillLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@AllArgsConstructor
public class SkillPortfolioController {

    private SkillPortfolioService skillPortfolioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPortfolioEntry(@RequestBody SkillPortfolioRequest request) {
        skillPortfolioService.createPortfolioEntry(
                request.getStaffMemberId(),
                request.getSkillId(),
                SkillLevel.valueOf(request.getSkillLevel()),
                request.getExpiryDate() != null ? LocalDate.parse(request.getExpiryDate()) : null
        );
    }

    @PatchMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editPortfolioEntry(@PathVariable String id,
                                   @RequestBody SkillPortfolioRequest request) {
        skillPortfolioService.editPortfolioEntry(
                id,
                SkillLevel.valueOf(request.getSkillLevel()),
                request.getExpiryDate() != null ? LocalDate.parse(request.getExpiryDate()) : null
        );
    }

    @GetMapping
    public List<SkillPortfolioResponse> getAllEntries() {
        return skillPortfolioService.getAllEntries();
    }

    @GetMapping("/{id}")
    public SkillPortfolioResponse getEntryById(@PathVariable String id) {
        return skillPortfolioService.getEntryById(id);
    }

    @GetMapping("/pending")
    public List<SkillPortfolioResponse> getPendingEntries() {
        return skillPortfolioService.getPendingEntries();
    }

    @GetMapping("/expired")
    public List<SkillPortfolioResponse> getExpiredEntries() {
        return skillPortfolioService.getExpiredEntries();
    }

    @PatchMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyEntry(@PathVariable String id,
                            @RequestBody SkillPortfolioRequest request) {
        skillPortfolioService.verifyEntry(id, request.getStaffMemberId());
    }

    @PatchMapping("/{id}/unverify")
    @ResponseStatus(HttpStatus.OK)
    public void unverifyEntry(@PathVariable String id) {
        skillPortfolioService.unverifyEntry(id);
    }

    @PatchMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    public void rejectEntry(@PathVariable String id,
                            @RequestBody SkillPortfolioRequest request) {
        skillPortfolioService.rejectEntry(id, request.getStaffMemberId());
    }

    @PostMapping("/{id}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNote(@PathVariable String id,
                        @RequestBody AddNoteRequest request) {
        skillPortfolioService.addNote(id, request.getNote(), request.getManagerId());
    }
}
