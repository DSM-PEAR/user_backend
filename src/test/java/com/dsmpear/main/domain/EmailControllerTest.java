package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.verifynumber.VerifyNumber;
import com.dsmpear.main.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.payload.request.EmailVerifyRequest;
import com.dsmpear.main.payload.request.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
public class EmailControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private VerifyNumberRepository verifyNumberRepository;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        verifyNumberRepository.save(
                VerifyNumber.builder()
                .email("smoothbear@dsm.hs.kr")
                .verifyNumber("1111")
                .build()
        );
    }

    @AfterEach
    public void after() {
        verifyNumberRepository.deleteAll();
    }

    @Test
    public void authNumEmailTestWithBadRequest() throws Exception {
        mvc.perform(get("/email/auth")
                .param("email", "smoothbear")
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void verifyAccountTest() throws Exception {
        mvc.perform(put("/email/auth")
                .content(new ObjectMapper().
                        writeValueAsString(new EmailVerifyRequest("1111", "smoothbear@dsm.hs.kr"))
                )
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void verifyAccountTestWithNumberNotFoundException() throws Exception {
        mvc.perform(put("/email/auth")
                .content(new ObjectMapper().
                        writeValueAsString(new EmailVerifyRequest("1234", "smoothbear@dsm.hs.kr"))
                )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andDo(print());
    }

    @Test
    public void notificationTestWithSecretKeyNotMatchedExcept() throws Exception {
        mvc.perform(post("/email/notification")
                .content(new ObjectMapper().writeValueAsString(new NotificationRequest("1000", "smoothbear@dsm.hs.kr", "",true))
                ).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "abcabc")
        ).andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    public void notificationTestWithBadRequest() throws Exception {
        mvc.perform(post("/email/notification")
                .content(new ObjectMapper().writeValueAsString(NotificationRequest.builder().body("안됨").email("smoothbear@dsm.hs.kr").build())
                ).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "abcabc")
        ).andExpect(status().isBadRequest()).andDo(print());
    }
}
