package com.dsmpear.main.domain;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.request.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

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

        userRepository.save(
                User.builder()
                        .email("test@dsm.hs.kr")
                        .name("홍길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("tset@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1234"))
                        .authStatus(true)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .reportId(1)
                        .userEmail("test@dsm.hs.kr")
                        .build()
        );
    }


    @After
    public void after () {
        memberRepository.deleteAll();
        reportRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "test@dsm.hs.kr",password = "1111")
    public void addMember() throws Exception {
        int reportId = addReport();

        MemberRequest request = new MemberRequest(reportId,"tset@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @Test
    @Order(1)
    @WithMockUser(username = "",password = "")
    public void addMember_noExistUser() throws Exception {
        int reportId = addReport();

        MemberRequest request = new MemberRequest(reportId,"tset@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    //로그인하지 않았을 때
    @Test
    @Order(1)
    public void addMember_noLogin() throws Exception {
        int reportId = addReport();

        MemberRequest request = new MemberRequest(reportId,"tset@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "tset@dsm.hs.kr",password = "1111")
    public void deleteMember() throws Exception{
        int reportId = addReport();

        mvc.perform(delete("/member/"+reportId))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "tset@dsm.hs.kr",password = "1111")
    public void deleteMember_noId() throws Exception{
        mvc.perform(delete("/member"))
                .andExpect(status().is4xxClientError()).andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser(username = "",password = "")
    public void deleteMember_noLogin() throws Exception{
        int reportId = addReport();

        mvc.perform(delete("/member/"+reportId))
                .andExpect(status().isNotFound());
    }

    //로그인하지 않았을 때


    private Integer addReport() {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("test")
                        .grade(Grade.GRADE2)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isAccepted(true)
                        .languages("test")
                        .fileName("test")
                        .createdAt(LocalDateTime.now())
                        .build()
        ).getReportId();

        memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail("test@dsm.hs.kr")
                        .build()
        );
        return reportId;
    }
}