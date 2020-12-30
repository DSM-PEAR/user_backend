package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.userreport.UserReport;
import com.dsmpear.main.entity.userreport.UserReportRepository;
import com.dsmpear.main.payload.request.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .email("test@dsm.hs.kr")
                        .name("홍길동")
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("tset@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("dsm@dsm.hs.kr")
                        .name("강아지")
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("flower@dsm.hs.kr")
                        .name("해바라기")
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .build()
        );
    }


    @AfterEach
    public void after () {
        memberRepository.deleteAll();
        reportRepository.deleteAll();
        userRepository.deleteAll();
        userReportRepository.deleteAll();
    }

    @Test
    public void getMember() throws Exception {
        Integer reportId = addReport();

        mvc.perform(get("/member/"+reportId+"?size=1&page=1"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getMember_no_report() throws Exception {

        mvc.perform(get("/member/1?size=1&page=1"))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @Order(1)
    @WithMockUser(username = "test@dsm.hs.kr",password = "1111")
    public void addMember() throws Exception {
        int reportId = addReport();

        MemberRequest request = new MemberRequest(reportId,"flower@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    @Order(1)
    @WithMockUser(username = "test@dsm.hs.kr",password = "1111")
    public void addMember_already() throws Exception {
        int reportId = addReport();

        MemberRequest request = new MemberRequest(reportId,"tset@dsm.hs.kr");

        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict()).andDo(print());
    }

    @Test
    @Order(1)
    @WithMockUser(username = "dms@dsm.hs.kr",password = "1111")
    public void addMember_notmember() throws Exception {
        int reportId = addReport();
        MemberRequest request = new MemberRequest(reportId,"flower@dsm.hs.kr");
        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden()).andDo(print());
    }
    //로그인하지 않았을 때
    @Test
    @Order(1)
    @WithMockUser()
    public void addMember_noLogin() throws Exception {
        int reportId = addReport();
        MemberRequest request = new MemberRequest(reportId,"dsm@dsm.hs.kr");
        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }
    @Test
    @Order(1)
    @WithMockUser(username = "test@dsm.hs.kr",password = "1111")
    public void addMember_exist_member() throws Exception {
        int reportId = addReport();
        MemberRequest request = new MemberRequest(reportId,"tset@dsm.hs.kr");
        mvc.perform(post("/member").
                content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict());
    }
    @Test
    @Order(2)
    @WithMockUser(value = "tset@dsm.hs.kr",password = "1111")
    public void deleteMember() throws Exception{
        Integer memberId = check_member();
        mvc.perform(delete("/member/"+memberId))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "test@dsm.hs.kr",password = "1111")
    public void deleteMember_me() throws Exception{
        Integer memberId = check_member();

        mvc.perform(delete("/member/"+memberId))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @Order(2)
    @WithMockUser(username = "",password = "")
    public void deleteMember_noLogin() throws Exception{
        Integer memberId = check_member();

        mvc.perform(delete("/member/"+memberId))
                .andExpect(status().isForbidden());
    }

    //로그인하지 않았을 때


    private Integer addReport() {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isSubmitted(false)
                        .accepted(0)
                        .createdAt(LocalDateTime.now())
                        .github("깃허브으")
                        .languages("자바")
                        .fileName("나는야 천재")
                        .teamName("룰루랄라")
                        .build()
        ).getId();

        Member member = memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail("test@dsm.hs.kr")
                        .build()
        );

        Member member1 = memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail("tset@dsm.hs.kr")
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail("test@dsm.hs.kr")
                        .reportId(reportId)
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail("tset@dsm.hs.kr")
                        .reportId(reportId)
                        .build()
        );

        System.out.println(member.getId());
        System.out.println(member1.getId());
        return reportId;
    }

    private Integer check_member() {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isSubmitted(false)
                        .accepted(0)
                        .createdAt(LocalDateTime.now())
                        .github("깃허브으")
                        .languages("자바")
                        .fileName("나는야 천재")
                        .teamName("룰루랄라")
                        .build()
        ).getId();

        Member member = memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail("test@dsm.hs.kr")
                        .build()
        );

        Member member1 = memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail("tset@dsm.hs.kr")
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail("test@dsm.hs.kr")
                        .reportId(reportId)
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail("tset@dsm.hs.kr")
                        .reportId(reportId)
                        .build()
        );

        System.out.println(member.getId());
        System.out.println(member1.getId());

        return member.getId();
    }
}