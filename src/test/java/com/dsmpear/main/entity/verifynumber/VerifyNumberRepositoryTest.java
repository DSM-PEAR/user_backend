package com.dsmpear.main.entity.verifynumber;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.config.EmbeddedRedisConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MainApplication.class, EmbeddedRedisConfig.class})
public class VerifyNumberRepositoryTest {
    @Autowired
    private VerifyNumberRepository verifyNumberRepository;

    @BeforeEach
    public void setup() throws Exception {
        verifyNumberRepository.save(
                VerifyNumber.builder()
                .email("test@dsm.hs.kr")
                .number(1111)
                .build()
        );
    }

    @AfterEach
    public void exit() {
        verifyNumberRepository.deleteAll();
    }

    @Test
    @DisplayName("verify number repository findByEmail test")
    public void verifyNumberRepositoryFindByEmail() {
        assertEquals(1111, verifyNumberRepository.findByEmail("test@dsm.hs.kr").get().getNumber());
    }
}
