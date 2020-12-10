package com.dsmpear.main.domain;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.response.SearchListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SearchControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .email("test@dsm.hs.kr")
                        .name("홍길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("hihihihi")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("tset@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("ptest@dsm.hs.kr")
                        .name("김길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("etest@dsm.hs.kr")
                        .name("이길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
    }

    @After
    public void after () {
        userRepository.deleteAll();
    }

    //왜 다 404야..
    @Test
    public void searchProfile () throws Exception {
        mvc.perform(get("/search?mode=profile&keyword=길동&size=10&page=0")).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void searchProfile_noKeyword () throws Exception {
        mvc.perform(get("/search?mode=profile&keyword=&size=10&page=0")).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void searchProfile_notFound () throws Exception {
        MvcResult result = mvc.perform(get("/search?mode=profile&keyword=동글이&size=10&page=0")).andReturn();

        SearchListResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SearchListResponse.class);
        Assert.assertEquals(response.getTotalElements(), 0);
    }

}
