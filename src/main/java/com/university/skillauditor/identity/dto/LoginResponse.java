package com.university.skillauditor.identity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginResponse(
        @JsonProperty("localId") String uid,
        String email,
        @JsonProperty("displayName") String username,
        @JsonProperty("idToken") String accessToken,
        String refreshToken,
        @JsonProperty("expiresIn") String expiresInSeconds
) {}