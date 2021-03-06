package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.payload.request.QuestionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
class QuestionControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void question() throws Exception {

        String email="test@dsm.hs.kr";
        String description="description";

        QuestionRequest request = new QuestionRequest(email,description);
      
        mvc.perform(post("/question")
                .content(new ObjectMapper()
                        .writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void question_notEmail() throws Exception {

        String description="description";

        QuestionRequest request = new QuestionRequest("",description);

        mvc.perform(post("/question")
                .content(new ObjectMapper()
                        .writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void question_noDescription() throws Exception {
        String email="test@dsm.hs.kr";

        QuestionRequest request = new QuestionRequest(email,"");

        mvc.perform(post("/question")
                .content(new ObjectMapper()
                        .writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }


}
