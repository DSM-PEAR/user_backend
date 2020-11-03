package com.dsmpear.main.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    @JsonProperty("access-token")
    private String accessToken;

    @JsonProperty("refresh-token")
    private String refreshToken;

    @JsonProperty("refresh-exp")
    private Long refreshExp;
}
