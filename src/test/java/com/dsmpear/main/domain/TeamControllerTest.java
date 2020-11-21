package com.dsmpear.main.domain;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.request.TeamRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeamControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReportRepository reportRepository;

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
    }

    @After
    public void after() {
        memberRepository.deleteAll();
        teamRepository.deleteAll();
        reportRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    @WithMockUser(value = "tset@dsm.hs.kr",password = "1111")
    public void addTeam() throws Exception{

        TeamRequest team = TeamRequest.builder()
                .reportId(writeReport())
                .name("second")
                .build();

        mvc.perform(post("/team").
                content(new ObjectMapper().writeValueAsString(team))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "test@dsm.hs.kr",password = "1111")
    public void  modifyName() throws Exception{

        int teamId = createTeam(writeReport());
        addMember(teamId);
        mvc.perform(patch("/team/"+teamId)
                .param("name", "ffirst")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

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