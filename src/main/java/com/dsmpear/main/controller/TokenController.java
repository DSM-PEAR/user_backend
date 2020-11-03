package com.dsmpear.main.controller;

import com.dsmpear.main.domain.auth.dto.request.SignInRequest;
import com.dsmpear.main.domain.auth.dto.response.AccessTokenResponse;
import com.dsmpear.main.domain.auth.dto.response.TokenResponse;
import com.dsmpear.main.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class TokenController {
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