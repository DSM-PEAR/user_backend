package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.refreshtoken.RefreshToken;
import com.dsmpear.main.entity.refreshtoken.RefreshTokenRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.request.SignInRequest;
import com.dsmpear.main.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles({"test"})
public class AuthControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository tokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    String accessToken;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        accessToken = jwtTokenProvider.generateAccessToken("smoothbear@dsm.hs.kr");

        userRepository.save(
                User.builder()
                .email("aaaa@dsm.hs.kr")
                .password(passwordEncoder.encode("1111"))
                .authStatus(true)
                .name("aaaa")
                .build()
        );

        refreshTokenRepository.save(
                RefreshToken.builder()
                .email("smoothbear@dsm.hs.kr")
                .refreshToken("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MDc4NjY1OTQsInN1YiI6ImFhYWFAZHNtLmhzLmtyIiwiZXhwIjoxNjA3ODgxNTk0LCJ0eXBlIjoicmVmcmVzaF90b2tlbiJ9.PaFogQhIeAo-PvyqQTxmPE3HCDyJSTknME0LwfefNsg")
                .refreshExp(500L)
                .build()
        );
    }

    @AfterEach
    public void exit() {
        userRepository.deleteAll();
        
        tokenRepository.deleteAll();
        
        refreshTokenRepository.deleteAll();
    }

    @Test
    void signInTest() throws Exception {
        SignInRequest signInRequest = new SignInRequest("aaaa@dsm.hs.kr", "1111");

        mvc.perform(post("/auth").content(new ObjectMapper().writeValueAsString(signInRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void refreshTokenTest() throws Exception {
        mvc.perform(put("/auth")
            .header("X-Refresh-Token", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MDc4NjY1OTQsInN1YiI6ImFhYWFAZHNtLmhzLmtyIiwiZXhwIjoxNjA3ODgxNTk0LCJ0eXBlIjoicmVmcmVzaF90b2tlbiJ9.PaFogQhIeAo-PvyqQTxmPE3HCDyJSTknME0LwfefNsg")
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void refreshTokenTestWithExpect() throws Exception {
        mvc.perform(put("/auth")
                .header("X-Refresh-Token", "apple")
        ).andExpect(status().isForbidden()).andDo(print());
    }

    @Test
    void refreshTokenTestWithIsNotRefreshTokenExcept() throws Exception {
        mvc.perform(put("/auth")
                .header("X-Refresh-Token", accessToken)
        ).andExpect(status().isForbidden()).andDo(print());
    }
}
