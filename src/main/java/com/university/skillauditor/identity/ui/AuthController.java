package com.university.skillauditor.identity.ui;

import com.university.skillauditor.identity.authService.FirebaseAuthService;
import com.university.skillauditor.identity.dto.LoginRequest;
import com.university.skillauditor.identity.dto.LoginResponse;
import com.university.skillauditor.identity.dto.RegisterRequest;
import com.university.skillauditor.identity.dto.RegisterResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Profile("!test")
public class AuthController {

    private final FirebaseAuthService firebaseAuthService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest request) throws Exception {
        return firebaseAuthService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return firebaseAuthService.loginUser(request);
    }
}