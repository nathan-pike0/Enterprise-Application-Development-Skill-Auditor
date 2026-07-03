package com.university.skillauditor.skillmanagement.api;
import com.university.skillauditor.skillmanagement.application.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
@AllArgsConstructor
public class SkillController {
    private SkillService skillService;

    @GetMapping
    public Iterable<SkillResponse> getAll() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public SkillResponse getById(@PathVariable String id) {
        return skillService.getSkillById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SkillRequest request) {
        skillService.createSkill(
                request.getName(),
                request.getDescription(),
                request.getCategory()
        );
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id,
                       @RequestBody SkillRequest request) {
        skillService.updateSkill(
                id,
                request.getName(),
                request.getDescription(),
                request.getCategory()
        );
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivate(@PathVariable String id) {
        skillService.deactivateSkill(id);
    }

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activate(@PathVariable String id) {
        skillService.activateSkill(id);
    }
}