package com.dsmpear.main.service.auth;

import com.dsmpear.main.payload.request.SignInRequest;
import com.dsmpear.main.payload.response.AccessTokenResponse;
import com.dsmpear.main.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest request);
    AccessTokenResponse tokenRefresh(String receivedToken);
}
