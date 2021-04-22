package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.userreport.UserReport;
import com.dsmpear.main.entity.userreport.UserReportRepository;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.request.SetSelfIntroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
        userReportRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void getMyProfile_test() throws Exception {
        mvc.perform(get("/user/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "tset@dsm.hs.kr", password = "1111")
    public void getMyProfile_tset () throws Exception {
        mvc.perform(get("/user/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void modifySelfIntro_test () throws Exception {

        String expectedGithub = "https://github.com/syxxn";
        String expectedIntro = "introduce";
        SetSelfIntroRequest request = SetSelfIntroRequest.builder()
                .github(expectedGithub)
                .intro(expectedIntro)
                .build();

        mvc.perform(put("/user/profile")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(userRepository.findByEmail("test@dsm.hs.kr").get().getSelfIntro(), expectedIntro);
        Assertions.assertEquals(userRepository.findByEmail("test@dsm.hs.kr").get().getGitHub(), expectedGithub);
    }

    @Test
    @WithMockUser(value = "test1sdjfk@dsm.hs.kr", password = "1111")
    public void modifySelfIntro_usernotfound () throws Exception {

        String expectedGithub = "https://github.com/syxxn";
        String expectedIntro = "introduce";
        SetSelfIntroRequest request = SetSelfIntroRequest.builder()
                .github(expectedGithub)
                .intro(expectedIntro)
                .build();

        mvc.perform(put("/user/profile")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "tset@dsm.hs.kr", password = "1111")
    public void modifySelfIntro_tset () throws Exception {

        String expectedGithub = "https://github.com/syxxn";
        String expectedIntro = "introduce";
        SetSelfIntroRequest request = SetSelfIntroRequest.builder()
                .github(expectedGithub)
                .intro(expectedIntro)
                .build();

        mvc.perform(put("/user/profile")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(userRepository.findByEmail("tset@dsm.hs.kr").get().getSelfIntro(), expectedIntro);
        Assertions.assertEquals(userRepository.findByEmail("tset@dsm.hs.kr").get().getGitHub(), expectedGithub);
    }

    //보고서 목록
    @Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void  getReportList_test() throws Exception{

        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_true("test@dsm.hs.kr");
        addReport_accepted_true("test@dsm.hs.kr");
        addReport_rejected_true("test@dsm.hs.kr");
        addReport_accepted_true("tset@dsm.hs.kr");


        mvc.perform(get("/user/profile/report"))
                .andExpect(status().isOk());
    }

    //보고서 목록
    @Test
    @WithMockUser(value = "tset@dsm.hs.kr", password = "1111")
    public void getReportList_tset() throws Exception{

        addReport_sub_false("test@dsm.hs.kr");
        addReport_sub_true("test@dsm.hs.kr");
        addReport_accepted_true("test@dsm.hs.kr");
        addReport_rejected_true("test@dsm.hs.kr");
        addReport_accepted_true("tset@dsm.hs.kr");

        mvc.perform(get("/user/profile/report"))
                .andExpect(status().isOk());
    }

    private Report addReport_sub_false(String email) {
        Report report = reportRepository.save(
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
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        memberRepository.save(
                Member.builder()
                        .report(report)
                        .userEmail(user.getEmail())
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .user(user)
                        .report(report)
                        .build()
        );

        return report;
    }

    private Report addReport_sub_true(String email) {
        Report report = reportRepository.save(
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
        );

        memberRepository.save(
                Member.builder()
                        .report(report)
                        .userEmail(email)
                        .build()
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        userReportRepository.save(
                UserReport.builder()
                        .user(user)
                        .report(report)
                        .build()
        );

        return report;
    }

    private Report addReport_accepted_true(String email) {
        Report report = reportRepository.save(
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
        );

        memberRepository.save(
                Member.builder()
                        .report(report)
                        .userEmail(email)
                        .build()
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        userReportRepository.save(
                UserReport.builder()
                        .user(user)
                        .report(report)
                        .build()
        );

        return report;
    }

    private Report addReport_rejected_true(String email) {
        Report report = reportRepository.save(
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
        );

        memberRepository.save(
                Member.builder()
                        .report(report)
                        .userEmail(email)
                        .build()
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        userReportRepository.save(
                UserReport.builder()
                        .report(report)
                        .user(user)
                        .build()
        );

        return report;
    }

}