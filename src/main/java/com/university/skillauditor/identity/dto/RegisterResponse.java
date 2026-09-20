package com.university.skillauditor.identity.dto;

public record RegisterResponse(
        String uid,
        String email,
        String username,
        String role
) {}