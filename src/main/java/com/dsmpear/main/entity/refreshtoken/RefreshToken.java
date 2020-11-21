package com.dsmpear.main.entity.refreshtoken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
=======
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;
>>>>>>> develop

import java.io.Serializable;

<<<<<<< HEAD
@Entity
@Table(name = "refresh_token")
=======
@RedisHash(value = "refresh_token")
>>>>>>> develop
@NoArgsConstructor @AllArgsConstructor @Getter
@Builder
public class RefreshToken implements Serializable {
    @Id
    private String email;

<<<<<<< HEAD
    @Column(nullable = false)
=======
    @Indexed
>>>>>>> develop
    private String refreshToken;

    @Column(nullable = false)
    private Long refreshExp;

    public RefreshToken update(String refreshToken, Long refreshExp) {
        this.refreshToken = refreshToken;
        this.refreshExp = refreshExp;
        return this;
    }
}
