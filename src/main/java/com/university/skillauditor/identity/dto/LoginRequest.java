package com.university.skillauditor.identity.dto;

public record LoginRequest(
        String email,
        String password
) {}