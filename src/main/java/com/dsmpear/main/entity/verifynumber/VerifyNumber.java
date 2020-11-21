package com.dsmpear.main.entity.verifynumber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "verify_number", timeToLive = 60 * 3)
@Getter @AllArgsConstructor @NoArgsConstructor @Builder
public class VerifyNumber {
    @Id
    private Integer id;

    @Indexed
    private String email;

    private String verifyNumber;

    @TimeToLive
    private Long ttl;

    public boolean verifyNumber(String number) {
        return this.verifyNumber.equals(number);
    }
}
