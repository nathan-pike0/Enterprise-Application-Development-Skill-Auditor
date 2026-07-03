package com.university.skillauditor.skillmanagement.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillRequest {
    private String name;
    private String description;
    private String category;
}