package com.dsmpear.main.service.auth;

import com.dsmpear.main.domain.auth.dto.request.SignInRequest;
import com.dsmpear.main.domain.auth.dto.response.AccessTokenResponse;
import com.dsmpear.main.domain.auth.dto.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest request);
    AccessTokenResponse tokenRefresh(String receivedToken);
}
