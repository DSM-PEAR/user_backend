package com.dsmpear.main.service.auth;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.exceptions.InvalidTokenException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles({"test"})
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void refreshTokenWithTokenException() {
        assertThrows(InvalidTokenException.class, () -> {
            authService.tokenRefresh("asdf");
        });
    }
}