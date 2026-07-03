package com.university.skillauditor.skillmanagement.application;

import com.university.skillauditor.shared.Identity;
import com.university.skillauditor.skillmanagement.domain.Skill;
import com.university.skillauditor.skillmanagement.domain.SkillStatus;
import com.university.skillauditor.skillmanagement.infrastructure.SkillEntity;
import com.university.skillauditor.skillmanagement.infrastructure.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.university.skillauditor.skillmanagement.api.SkillResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SkillService {
    private SkillRepository skillRepository;

    public void createSkill(String name, String description, String category) {
        Identity<Skill> id = Identity.generateId();

        Skill skill = new Skill(id, name, description, category, SkillStatus.ACTIVE);

        SkillEntity entity = new SkillEntity();
        entity.setId(skill.id().id());
        entity.setName(skill.name());
        entity.setDescription(skill.description());
        entity.setCategory(skill.category());
        entity.setStatus(skill.status().name());

        skillRepository.save(entity);
    }

    public Iterable<SkillResponse> getAllSkills() {
        Iterable<SkillEntity> entities = skillRepository.findAll();
        List<SkillResponse> skills = new ArrayList<>();
        for (SkillEntity entity : entities) {
            skills.add(new SkillResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getCategory(),
                    entity.getStatus()
            ));
        }
        return skills;
    }

    public SkillResponse getSkillById(String id) {
        Optional<SkillEntity> result = skillRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found");
        }
        SkillEntity entity = result.get();
        return new SkillResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getStatus()
        );
    }

    public void updateSkill(String id, String name, String description, String category) {
        Optional<SkillEntity> result = skillRepository.findById(id);
        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        SkillEntity entity = result.get();
        entity.setName(name);
        entity.setDescription(description);
        entity.setCategory(category);

        skillRepository.save(entity);

    }

    public void deactivateSkill(String id) {
        Optional<SkillEntity> result = skillRepository.findById(id);
        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
        SkillEntity entity = result.get();

        Skill skill = new Skill(
                Identity.of(entity.getId()),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                SkillStatus.valueOf(entity.getStatus())
        );

        skill.disableSkill();

        entity.setStatus(skill.status().name());
        skillRepository.save(entity);

    }

    public void activateSkill(String id) {
        Optional<SkillEntity> result = skillRepository.findById(id);
        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skill not found");
        }
        SkillEntity entity = result.get();
        Skill skill = new Skill(
                Identity.of(entity.getId()),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                SkillStatus.valueOf(entity.getStatus())
        );
        skill.enableSkill();
        entity.setStatus(skill.status().name());
        skillRepository.save(entity);
    }


}