package com.dsmpear.main.domain;

import com.dsmpear.main.entity.verifynumber.VerifyNumber;
import com.dsmpear.main.entity.verifynumber.VerifyNumberRepository;
import com.dsmpear.main.payload.request.EmailVerifyRequest;
import com.dsmpear.main.payload.request.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
@SpringBootTest
@ActiveProfiles("test")
public class EmailControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private VerifyNumberRepository verifyNumberRepository;

    private MockMvc mvc;

    @Before
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

    @After
    public void after() {
        verifyNumberRepository.deleteAll();
    }


    @Test
    public void authNumEmailTest() throws Exception {
        mvc.perform(get("/email/auth")
            .param("email", "smoothbear@dsm.hs.kr")
        ).andExpect(status().isOk()).andDo(print());
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
    public void notificationTest() throws Exception {
        mvc.perform(post("/email/notification")
                .content(new ObjectMapper().writeValueAsString(new NotificationRequest("1000", "smoothbear@dsm.hs.kr", true))
                ).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "secret")
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void notificationTestWithSecretKeyNotMatchedExcept() throws Exception {
        mvc.perform(post("/email/notification")
                .content(new ObjectMapper().writeValueAsString(new NotificationRequest("1000", "smoothbear@dsm.hs.kr", true))
                ).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "abcabc")
        ).andExpect(status().isUnauthorized()).andDo(print());
    }
}
