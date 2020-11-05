package com.dsmpear.main.security;

import com.dsmpear.main.exceptions.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setup() throws Exception {
    }

    @Test
    @DisplayName("resolveToken test")
    public void testResolveToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "dddddd access-token");
        assertEquals("access-token", jwtTokenProvider.resolveToken(request));
    }

    @Test
    @DisplayName("validateToken test")
    public void testValidateToken() {
        Throwable exception = assertThrows(InvalidTokenException.class, () -> {
            throw new InvalidTokenException();
        });

        assertEquals(exception.getMessage(), "INVALID_TOKEN");
    }
}