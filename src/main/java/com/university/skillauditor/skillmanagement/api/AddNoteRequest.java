package com.university.skillauditor.skillmanagement.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddNoteRequest {
    private String note;
    private String managerId;
}