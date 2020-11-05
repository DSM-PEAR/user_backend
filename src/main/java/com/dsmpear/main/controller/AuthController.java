package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.SignInRequest;
import com.dsmpear.main.payload.response.AccessTokenResponse;
import com.dsmpear.main.payload.response.TokenResponse;
import com.dsmpear.main.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public TokenResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }

    @PutMapping
    public AccessTokenResponse tokenRefresh(@RequestHeader("Refresh-Token") String refreshToken) {
        return authService.tokenRefresh(refreshToken);
    }
}