package com.dsmpear.main.entity.verifyuser;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.exceptions.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MainApplication.class})
@ActiveProfiles({"test"})
public class VerifyUserRepositoryTest {
    @Autowired
    private VerifyUserRepository verifyUserRepository;

    private VerifyUser verifyUser;

    @BeforeEach
    public void setup() throws Exception {
        verifyUser = verifyUserRepository.save(
                VerifyUser.builder()
                        .email("smoothbear@dsm.hs.kr")
                        .ttl(500L)
                        .build()
        );
    }

    @AfterEach
    public void exit() {
        verifyUserRepository.deleteAll();
    }

    @Test
    public void verifyUserRepositoryFindByEmail() {
        VerifyUser user = verifyUserRepository.findByEmail("smoothbear@dsm.hs.kr")
                .orElseThrow(UserNotFoundException::new);

        assertEquals(verifyUser.getEmail(), user.getEmail());
        assertEquals(verifyUser.getTtl(), user.getTtl());
        assertEquals(verifyUser.getUUID(), user.getUUID());
    }
}
