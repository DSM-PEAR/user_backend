package com.dsmpear.main.entity.verifynumber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "verify_number", timeToLive = 60 * 3)
@Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class VerifyNumber {
    @Id
    private String email;

    private Integer number;

    @TimeToLive
    private Long ttl;

    public boolean verifyNumber(int number) {
        return this.number.equals(number);
    }
}
