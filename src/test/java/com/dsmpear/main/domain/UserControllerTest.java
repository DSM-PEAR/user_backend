package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.verifyuser.VerifyUser;
import com.dsmpear.main.entity.verifyuser.VerifyUserRepository;
import com.dsmpear.main.payload.request.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifyUserRepository verifyUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        verifyUserRepository.save(
                VerifyUser.builder().email("smoothbear@dsm.hs.kr").build()
        );

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
                .email("alreadyuser@dsm.hs.kr")
                .password(passwordEncoder.encode("1111"))
                .authStatus(true)
                .name("alreadyuser")
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

    @AfterEach
    public void after() {
        userRepository.deleteAll();
        verifyUserRepository.deleteAll();
    }


    @Test
    public void registerUser() throws Exception {
        mvc.perform(post("/account").content(new ObjectMapper()
                .writeValueAsString(new RegisterRequest("smoothbear", "1111", "smoothbear@dsm.hs.kr")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void registerUserWithEmailExcept() throws Exception {
        mvc.perform(post("/account").content(new ObjectMapper()
                .writeValueAsString(new RegisterRequest("smoothbear", "1111", "smoothbear@naver.com")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andDo(print());
    }

    @Test
    public void registerUserWithUserIsAlreadyRegisteredExcept() throws Exception {
        mvc.perform(post("/account").content(new ObjectMapper()
                .writeValueAsString(new RegisterRequest("alreadyuser", "1111", "alreadyuser@dsm.hs.kr")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void registerUserWithUserNotFoundExcept() throws Exception {
        mvc.perform(post("/account").content(new ObjectMapper()
                .writeValueAsString(new RegisterRequest("smoothbear", "1111", "smoo@dsm.hs.kr")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser() throws Exception{
        mvc.perform(get("/account?name="))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser_existUser() throws Exception{
        mvc.perform(get("/account?name=홍길동"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser_bad() throws Exception{
        mvc.perform(get("/account"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "hihi@dsm.hs.kr",password = "1111")
    public void getUser_noLogin() throws Exception{
        mvc.perform(get("/account?name="))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "apple@dsm.hs.kr",password = "1111")
    public void getUser_notFound() throws Exception{
        mvc.perform(get("/account?name=가랑가랑"))
                .andExpect(status().isNotFound());
    }
}
