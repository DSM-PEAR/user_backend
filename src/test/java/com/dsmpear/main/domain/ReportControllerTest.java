package com.dsmpear.main.domain;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.request.ReportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @PostConstruct
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .email("test@dsm.hs.kr")
                        .name("홍길동")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("test1@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );


    }


    @Test
    @Order(1)
    @WithMockUser(value = "test@dsm.hs.kr",password="11123")
    public void createTest() throws Exception {

        ReportRequest request = ReportRequest.builder()
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .title("1. 이승윤 돼지")
                .description("내애용은 이승윤 돼지")
                .languages("자바")
                .type(Type.TEAM)
                .access(Access.EVERY)
                .grade(Grade.GRADE2)
                .field(Field.AI)
                .fileName("이승윤 돼지")
                .build();

        mvc.perform(post("/report").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "test@dsm.hs.kr",password="11123")
    public void createTeam() throws Exception {
        teamRepository.save(
                Team.builder()
                .name("이승유 돼지")
                .reportId(1)
                .userEmail("test@dsm.hs.kr")
                .build()
        );
    }
/*
    private Integer createReport() throws Exception {
        return Report.builder()
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .title("1. 이승윤 돼지")
                .description("내애용은 이승윤 돼지")
                .languages("자바")
                .type(Type.TEAM)
                .access(Access.EVERY)
                .grade(Grade.GRADE2)
                .isAccepted(0)
                .field(Field.AI)
                .fileName("이승윤 돼지")
                .reportId(1)
                .build().getReportId();
    }*/


    @Test
    @Order(3)
    @WithMockUser(value = "tset@dsm.hs.kr",password = "1111")
    public void getTeam() throws Exception{

        int reportId = writeReport();
        createTeam(reportId);
        mvc.perform(get("/team/"+reportId)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    private Integer writeReport() {
        return reportRepository.save(
                Report.builder()
                        .title("title")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(1)
                        .languages("C, JAVA")
                        .access(Access.ADMIN)
                        .build()
        ).getReportId();
    }

    private Integer createTeam(Integer reportId) {
        return teamRepository.save(
                Team.builder()
                        .reportId(reportId)
                        .name("first")
                        .userEmail("tset@dsm.hs.kr")
                        .build()
        ).getId();
    }

    private Integer addMember(Integer teamId) {
        return memberRepository.save(
                Member.builder()
                        .teamId(teamId)
                        .userEmail("test@dsm.hs.kr")
                        .build()
        ).getId();
    }

}
