package com.dsmpear.main.security.auth;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.user.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles({"test"})
class AuthDetailsTest {
    private AuthDetails authDetails = new AuthDetails(User.builder()
        .email("smoothbear@dsm.hs.kr")
            .name("부드러운 곰")
            .password("honey")
            .authStatus(true)
            .gitHub("smoothbear.github.com")
            .selfIntro("smoothbear")
            .build()
    );

    @Test
    void getAuthStatus() {
        assertNotNull(authDetails.getAuthorities());
    }

    @Test
    void getAuthorities() {
        assertNotNull(authDetails.getAuthorities());
    }

    @Test
    void getPassword() {
        assertNull(authDetails.getPassword());
    }

    @Test
    void getUsername() {
        assertNotNull(authDetails.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(authDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(authDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(authDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(authDetails.isEnabled());
    }
}