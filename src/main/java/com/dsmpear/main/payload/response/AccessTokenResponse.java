package com.dsmpear.main.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessTokenResponse {
    @JsonProperty("access_token")
    private final String accessToken;
}
