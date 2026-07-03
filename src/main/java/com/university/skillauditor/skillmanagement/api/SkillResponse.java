package com.university.skillauditor.skillmanagement.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SkillResponse {
    private String id;
    private String name;
    private String description;
    private String category;
    private String status;
}