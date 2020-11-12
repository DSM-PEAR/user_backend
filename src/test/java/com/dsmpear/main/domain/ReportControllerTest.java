package com.dsmpear.main.domain;

import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReportControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @PostConstruct
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        reportRepository.save(
                Report.builder()
                        .title("이승윤 돼지")
                        .description("이승윤 민머리")
                        .languages("이승윤 대머리")
                        .type(Type.TEAM)
                        .access(Access.EVERY)
                        .grade(Grade.GRADE2)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("tset@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );

        teamRepository.save(
                Team.builder()
                        .name("랄랄라")
                        .userEmail("test@dsm.hs.kr")
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .teamId(1)
                        .userEmail("test@dsm.hs.kr")
                        .build()
        );
    }

    @Test
    @Order(1)
    @WithMockUser(username = "test@dsm.hs.kr",password = "1111")
    void addMember() throws Exception {

        MemberRequest request = new MemberRequest(1,"tset@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    /*@Test
    @Order(2)
    @WithMockUser(value = "test@dsm.hs.kr",password = "1111")
    void deleteMember() throws Exception {
        mvc.perform(delete("/member/2"))
                .andExpect(status().isOk()).andDo(print());
    }*/

    @Test
    @Order(2)
    @WithMockUser(value = "tset@dsm.hs.kr",password = "1111")
    void deleteMember() throws Exception{

        mvc.perform(delete("/member/1"))
                .andExpect(status().isOk()).andDo(print());
    }
}