package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.userreport.UserReport;
import com.dsmpear.main.entity.userreport.UserReportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
class ProfileControllerTest {

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

    @AfterEach
    public void after () {
        reportRepository.deleteAll();
        memberRepository.deleteAll();
        userRepository.deleteAll();
        userReportRepository.deleteAll();
    }

    @Test
    public void getProfile () throws Exception {
        mvc.perform(get("/profile?user-email=test@dsm.hs.kr"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@dsm.hs.kr",password = "1234")
    public void getProfile_login () throws Exception {
        mvc.perform(get("/profile?user-email=test@dsm.hs.kr"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@dsm.hs.kr",password = "1234")
    public void getProfile_noUser () throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().isBadRequest());
    }

    //보고서 목록
    @Test
    public void getReportList() throws Exception{
        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_true("test@dsm.hs.kr");
        addReport_accepted_true("test@dsm.hs.kr");
        addReport_rejected_true("test@dsm.hs.kr");
        addReport_accepted_true("tset@dsm.hs.kr");
        addReport_accepted_true("test@dsm.hs.kr");

        mvc.perform(get("/profile/report?user-email=test@dsm.hs.kr&size=2&page=0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@dsm.hs.kr",password = "1234")
    public void  getReportList_isLogin() throws Exception{
        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_true("test@dsm.hs.kr");
        addReport_accepted_true("test@dsm.hs.kr");
        addReport_rejected_true("test@dsm.hs.kr");
        addReport_accepted_true("tset@dsm.hs.kr");

        mvc.perform(get("/profile/report?user-email=test@dsm.hs.kr&size=2&page=0"))
    }

    @Test
    public void  getReportList_notFound() throws Exception{
        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_true("test@dsm.hs.kr");
        addReport_accepted_true("test@dsm.hs.kr");
        addReport_rejected_true("test@dsm.hs.kr");
        addReport_accepted_true("tset@dsm.hs.kr");

        mvc.perform(get("/profile/report?user-email=lalalalala@dsm.hs.kr&size=2&page=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getReportList_notFound_isLogin() throws Exception{
        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_true("test@dsm.hs.kr");
        addReport_accepted_true("test@dsm.hs.kr");
        addReport_rejected_true("test@dsm.hs.kr");
        addReport_accepted_true("tset@dsm.hs.kr");

        mvc.perform(get("/profile/report?user-email=lalalalal@dsm.hs.kr&size=2&page=1"))
                .andExpect(status().isNotFound());
    }

    private Integer addReport_sub_false(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .comment(null)
                        .isSubmitted(false)
                        .isAccepted(false)
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getId();

        memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail(email)
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail(email)
                        .reportId(reportId)
                        .build()
        );

        return reportId;
    }

    private Integer addReport_sub_true(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE1)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isSubmitted(true)
                        .isAccepted(false)
                        .comment(null)
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getId();

        memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail(email)
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail(email)
                        .reportId(reportId)
                        .build()
        );

        return reportId;
    }

    private Integer addReport_accepted_true(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isSubmitted(true)
                        .isAccepted(true)
                        .comment(null)
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getId();

        memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail(email)
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail(email)
                        .reportId(reportId)
                        .build()
        );

        return reportId;
    }

    private Integer addReport_rejected_true(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isSubmitted(false)
                        .isAccepted(false)
                        .comment("반환합니다")
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getId();

        memberRepository.save(
                Member.builder()
                        .reportId(reportId)
                        .userEmail(email)
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .userEmail(email)
                        .reportId(reportId)
                        .build()
        );

        return reportId;
    }

}