package com.dsmpear.main.domain;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
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
        userRepository.deleteAll();
    }

    @Test
    public void getProfile () throws Exception {
        mvc.perform(get("/profile?user-email=test@dsm.hs.kr"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(username = "test@dsm.hs.kr",password = "1234")
    public void getProfile_login () throws Exception {
        mvc.perform(get("/profile?user-email=test@dsm.hs.kr"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getProfile_noUser () throws Exception {
        mvc.perform(get("/profile"))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    //보고서 목록
    @Test
    public void  getReportList() throws Exception{

        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_false("tset@dsm.hs.kr");
        addReport_sub_false_("test@dsm.hs.kr");
        addReport_sub_ture("test@dsm.hs.kr");
        addReport_sub_ture("tset@dsm.hs.kr");

        mvc.perform(get("/profile/report?user-email=test@dsm.hs.kr&size=2&page=1"))
                .andExpect(status().isOk()).andDo(print());
    }



    private Integer addReport_sub_false(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.ADMIN)
                        .field(Field.WEB)
                        .type(Type.TEAM)
                        .isSubmitted(false)
                        .isAccepted(true)
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getReportId();

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

    private Integer addReport_sub_false_(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.ADMIN)
                        .field(Field.WEB)
                        .type(Type.TEAM)
                        .isSubmitted(false)
                        .isAccepted(true)
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getReportId();

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

    private Integer addReport_sub_ture(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.ADMIN)
                        .field(Field.WEB)
                        .type(Type.TEAM)
                        .isSubmitted(false)
                        .isAccepted(true)
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getReportId();

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

    private Integer addReport_sub_ture_(String email) {
        Integer reportId = reportRepository.save(
                Report.builder()
                        .title("hello")
                        .description("hihello")
                        .grade(Grade.GRADE2)
                        .access(Access.ADMIN)
                        .field(Field.WEB)
                        .type(Type.TEAM)
                        .isSubmitted(false)
                        .isAccepted(true)
                        .createdAt(LocalDateTime.now())
                        .github("https://github.com")
                        .languages("자바, C")
                        .fileName("안녕한가파일")
                        .teamName("룰루랄라")
                        .build()
        ).getReportId();

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
