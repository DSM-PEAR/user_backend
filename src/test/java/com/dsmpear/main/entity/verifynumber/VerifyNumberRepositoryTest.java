package com.dsmpear.main.entity.verifynumber;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.config.EmbeddedRedisConfig;
import com.dsmpear.main.exceptions.UserNotFoundException;
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
@SpringBootTest(classes = {MainApplication.class, EmbeddedRedisConfig.class})
@ActiveProfiles({"test"})
public class VerifyNumberRepositoryTest {
    @Autowired
    private VerifyNumberRepository verifyNumberRepository;

    private VerifyNumber verifyNumber;

    @BeforeEach
    public void setup() throws Exception {
        verifyNumber = verifyNumberRepository.save(
                VerifyNumber.builder()
                        .email("test@dsm.hs.kr")
                        .verifyNumber("1111")
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
        VerifyNumber number = verifyNumberRepository.findByEmail("test@dsm.hs.kr")
                .orElseThrow(UserNotFoundException::new);

        assertEquals(verifyNumber.getVerifyNumber(), number.getVerifyNumber());
    }
}
