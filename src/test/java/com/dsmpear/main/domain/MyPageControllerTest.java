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
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
class MyPageControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserReportRepository userReportRepository;

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
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
        userRepository.save(
                User.builder()
                        .email("tset@dsm.hs.kr")
                        .name("고길동")
                        .password(passwordEncoder.encode("1111"))
                        .authStatus(true)
                        .selfIntro("lalalala")
                        .build()
        );
    }

    @AfterEach
    public void after() {
           memberRepository.deleteAll();
           reportRepository.deleteAll();
           userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void getMyProfile_test() throws Exception {
    mvc.perform(get("/user/profile"))
             .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(value = "tset@dsm.hs.kr", password = "1111")
    public void getMyProfile_tset () throws Exception {
        mvc.perform(get("/user/profile"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void modifySelfIntro_test () throws Exception {

        mvc.perform(put("/user/profile")
                .param("intro", "hihihihi")
                .param("gitHub","https://github.com/syxxn")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent()).andDo(print());
    }

    @Test
    @WithMockUser(value = "tset@dsm.hs.kr", password = "1111")
    public void modifySelfIntro_tset () throws Exception {

        mvc.perform(put("/user/profile")
                .param("intro", "hihihihi")
                .param("gitHub","https://github.com/syxxn")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent()).andDo(print());
    }

    //보고서 목록
    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void  getReportList() throws Exception{

        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_false("tset@dsm.hs.kr");
        addReport_sub_false_("test@dsm.hs.kr");
        addReport_sub_ture("test@dsm.hs.kr");
        addReport_sub_ture("tset@dsm.hs.kr");

        mvc.perform(get("/user/profile/report"))
                .andExpect(status().isOk()).andDo(print());
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
                        .isSubmitted(false)
                        .isAccepted(0)
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
                        .isAccepted(0)
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
                        .grade(Grade.GRADE1)
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isSubmitted(true)
                        .isAccepted(1)
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
                        .access(Access.EVERY)
                        .field(Field.AI)
                        .type(Type.TEAM)
                        .isSubmitted(true)
                        .isAccepted(2)
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
