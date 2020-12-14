package com.dsmpear.main.security;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.InvalidTokenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles({"test"})
public class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    String accessToken;
    String smoothAccessToken;

    @BeforeEach
    void setup() throws Exception {
        accessToken = jwtTokenProvider.generateAccessToken("smoothbear@dsm.hs.kr");
        smoothAccessToken = jwtTokenProvider.generateAccessToken("smooth@dsm.hs.kr");
        userRepository.save(
                User.builder()
                        .email("smoothbear@dsm.hs.kr")
                        .name("부드러운")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("smooth@dsm.hs.kr")
                        .name("부드러운")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(false)
                        .build()
        );
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("resolveToken test")
    public void testResolveToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "dddddd access-token");
        assertEquals("access-token", jwtTokenProvider.resolveToken(request));
    }

    @Test
    @DisplayName("resolveToken test")
    public void testResolveTokenWithNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertNull(jwtTokenProvider.resolveToken(request));
    }

    @Test
    @DisplayName("resolveToken test")
    public void testResolveTokenWithInvalidPrefix() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "access-token");
        assertNull(jwtTokenProvider.resolveToken(request));
    }

    @Test
    @DisplayName("validateToken test")
    public void testValidateTokenWithException() {
        Throwable exception = assertThrows(InvalidTokenException.class, () -> {
            jwtTokenProvider.validateToken("asdf");
        });

        assertEquals(exception.getMessage(), "INVALID_TOKEN");
    }

    @Test
    @DisplayName("validateToken test")
    public void testValidateToken() {
        assertTrue(jwtTokenProvider.validateToken(accessToken));
    }

    @Test
    @DisplayName("getEmail test")
    public void getEmail() {
        assertEquals("smoothbear@dsm.hs.kr", jwtTokenProvider.getEmail(accessToken));
    }

    @Test
    @DisplayName("getEmail test with exception")
    public void getEmailWithException() {
        assertThrows(InvalidTokenException.class, () -> {
            jwtTokenProvider.getEmail("honey");
        });
    }

    @Test
    @DisplayName("isEmailAuthenticated")
    public void isEmailAuthenticatedTest() {
        assertTrue(jwtTokenProvider.isEmailAuthenticated(accessToken));
    }

    @Test
    @DisplayName("isEmailAuthenticated")
    public void isNotEmailAuthenticatedTest() {
        assertFalse(jwtTokenProvider.isEmailAuthenticated(smoothAccessToken));
    }

    @Test
    @DisplayName("getAuthentication")
    public void getAuthenticationTest() {
        assertNotNull(jwtTokenProvider.getAuthentication(accessToken));
    }

    @Test
    @DisplayName("getAuthentication With Null")
    public void getAuthenticationWithExceptionTest() {
        assertThrows(InvalidTokenException.class, () -> {
            jwtTokenProvider.getAuthentication("honey");
        });
    }
}