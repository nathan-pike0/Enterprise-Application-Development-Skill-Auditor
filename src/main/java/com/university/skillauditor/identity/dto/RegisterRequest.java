package com.university.skillauditor.identity.dto;

public record RegisterRequest(
        String username,
        String email,
        String password,
        String role
) {}