package com.university.skillauditor.roleallocation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/roleallocations")
@AllArgsConstructor
public class RoleAllocationController {

    private RoleAllocationRepository roleAllocationRepository;

    @GetMapping
    public Iterable<RoleAllocation> getAll() {
        return roleAllocationRepository.findAll();
    }

    @GetMapping("/{id}")
    public RoleAllocation getById(@PathVariable Long id) {
        return roleAllocationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Role allocation not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody RoleAllocation roleAllocation) {
        roleAllocationRepository.save(roleAllocation);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @Valid @RequestBody RoleAllocation update) {
        RoleAllocation existing = roleAllocationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Role allocation not found"));
        existing.setName(update.getName());
        roleAllocationRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if (!roleAllocationRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role allocation not found");
        }
        roleAllocationRepository.deleteById(id);
    }
}