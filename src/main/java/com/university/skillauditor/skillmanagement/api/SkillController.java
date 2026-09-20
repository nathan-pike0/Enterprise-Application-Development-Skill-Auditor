package com.university.skillauditor.skillmanagement.api;
import com.university.skillauditor.skillmanagement.SkillManagementFacade;
import com.university.skillauditor.skillmanagement.application.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
@AllArgsConstructor
public class SkillController {

    private SkillManagementFacade skillManagementFacade;

    @GetMapping
    public Iterable<SkillResponse> getAll() {
        return skillManagementFacade.getAllSkills();
    }

    @GetMapping("/{id}")
    public SkillResponse getById(@PathVariable String id) {
        return skillManagementFacade.getSkillById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SkillRequest request) {
        skillManagementFacade.createSkill(request.getName(), request.getDescription(), request.getCategory());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestBody SkillRequest request) {
        skillManagementFacade.updateSkill(
                id,
                request.getName(),
                request.getDescription(),
                request.getCategory()
        );
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivate(@PathVariable String id) {
        skillManagementFacade.deactivateSkill(id);
    }

    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activate(@PathVariable String id) {
        skillManagementFacade.activateSkill(id);
    }
}