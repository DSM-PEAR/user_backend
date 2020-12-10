package com.dsmpear.main.domain;

import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .email("apple@dsm.hs.kr")
                        .name("홍길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("bear@dsm.hs.kr")
                        .name("고jam")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("cat@dsm.hs.kr")
                        .name("양jam")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("dear@dsm.hs.kr")
                        .name("강jam")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );

    }

    @After
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser() throws Exception{
        mvc.perform(get("/account?name="))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser_existUser() throws Exception{
        mvc.perform(get("/account?name=홍길동"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser_bad() throws Exception{
        mvc.perform(get("/account"))
                .andExpect(status().isBadRequest()).andDo(print());
    }

}
