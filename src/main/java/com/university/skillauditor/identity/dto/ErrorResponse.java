package com.university.skillauditor.identity.dto;

public record ErrorResponse(
        int status,
        String error,
        String message
) {}