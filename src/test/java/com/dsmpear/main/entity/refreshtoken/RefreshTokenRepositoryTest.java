package com.dsmpear.main.entity.refreshtoken;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.config.EmbeddedRedisConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmbeddedRedisConfig.class, MainApplication.class})
@ActiveProfiles({"test"})
public class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository repository;

    private RefreshToken refreshToken;

    @BeforeEach
    void setup() throws Exception {
        this.refreshToken = repository.save(
                RefreshToken.builder()
                .email("asdf@gmail.com")
                .refreshExp(500L)
                .refreshToken("asldfalskdfj")
                .build()
        );
    }

    @AfterEach
    void exit() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("refresh token repository test")
    public void findByRefreshTokenTest() {
        assertEquals(refreshToken.getRefreshToken(), repository.findByRefreshToken("asldfalskdfj").get().getRefreshToken());
    }
}
