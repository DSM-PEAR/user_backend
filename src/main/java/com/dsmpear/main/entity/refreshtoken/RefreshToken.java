package com.dsmpear.main.entity.refreshtoken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@RedisHash("refresh_token")
@NoArgsConstructor @AllArgsConstructor @Getter
@Builder
public class RefreshToken {
    @Id
    private String email;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Long refreshExp;

    public RefreshToken update(String refreshToken, Long refreshExp) {
        this.refreshToken = refreshToken;
        this.refreshExp = refreshExp;
        return this;
    }
}
