package com.university.skillauditor.identity.authService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.university.skillauditor.identity.dto.LoginRequest;
import com.university.skillauditor.identity.dto.LoginResponse;
import com.university.skillauditor.identity.dto.RegisterRequest;
import com.university.skillauditor.identity.dto.RegisterResponse;
import com.university.skillauditor.identity.security.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@Profile("!test")
public class FirebaseAuthService {

    private final FirebaseAuth firebaseAuth;
    private final RestClient restClient;
    private final String firebaseApiKey;

    public FirebaseAuthService(FirebaseAuth firebaseAuth, RestClient restClient,
                               @Value("${firebase.web-api-key}") String firebaseApiKey) {
        this.firebaseAuth = firebaseAuth;
        this.restClient = restClient;
        this.firebaseApiKey = firebaseApiKey;
    }

    public RegisterResponse registerUser(RegisterRequest request) throws FirebaseAuthException {
        Role role = Role.fromString(request.role());

        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setEmail(request.email())
                .setPassword(request.password())
                .setDisplayName(request.username());

        UserRecord userRecord = firebaseAuth.createUser(createRequest);
        firebaseAuth.setCustomUserClaims(userRecord.getUid(), Map.of("role", role.getAuthority()));

        return new RegisterResponse(userRecord.getUid(), userRecord.getEmail(),
                userRecord.getDisplayName(), role.getAuthority());
    }

    public LoginResponse loginUser(LoginRequest request) {
        String firebaseLoginUrl =
                "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + firebaseApiKey;

        Map<String, Object> requestBody = Map.of(
                "email", request.email(),
                "password", request.password(),
                "returnSecureToken", true
        );

        return restClient.post()
                .uri(firebaseLoginUrl)
                .body(requestBody)
                .retrieve()
                .body(LoginResponse.class);
    }
}