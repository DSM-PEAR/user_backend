package com.dsmpear.main.security.auth;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
class AuthDetailsServiceTest {
    @Autowired
    private AuthDetailsService authDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userRepository.save(
                User.builder()
                        .email("smoothbear@dsm.hs.kr")
                        .name("부드러운")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );
    }

    @Test
    @WithMockUser(value = "smoothbear@dsm.hs.kr", password = "1111")
    void testLoadUserByUsername() {
        assertNotNull(authDetailsService.loadUserByUsername("smoothbear@dsm.hs.kr"));
    }

    @Test
    @WithMockUser(value = "smoothbear@dsm.hs.kr", password = "1111")
    void testLoadUserByUsernameWithException() {
        assertThrows(UserNotFoundException.class, () -> {
            authDetailsService.loadUserByUsername("asdf");
        });
    }
}