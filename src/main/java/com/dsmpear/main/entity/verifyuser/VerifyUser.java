package com.dsmpear.main.entity.verifyuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@RedisHash(timeToLive = 60 * 3)
@Getter @AllArgsConstructor
public class VerifyUser {
    @Id
    private Integer UUID;

    @Indexed
    private final String email;

    @TimeToLive
    private Long ttl;

    public VerifyUser(String email) {
        this.email = email;
    }
}
