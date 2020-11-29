package com.dsmpear.main.domain;

import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProfileControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeamRepository teamRepository;

    private MockMvc mvc;

    @Before
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
    }

    @After
    public void after () {
        reportRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getProfile () throws Exception {
        mvc.perform(get("/profile/test@dsm.hs.kr"))
                .andExpect(status().isOk()).andDo(print());
    }

    //권한에 따라 보고서 목록 보여주기
    /*@Test
    @WithMockUser(value = "test@dsm.hs.kr", password = "1111")
    public void  getReportList() throws Exception{

        createTeam(writeReportAdmin());
        createTeam(writeReportEvery());
        createTeam(writeReportEvery2());
        createTeam(writeReportUser());

        mvc.perform(get("/user/profile/report"))
                .andExpect(status().isOk()).andDo(print());
    }

    private Integer writeReportUser() {
        return reportRepository.save(
                Report.builder()
                        .reportId(1)
                        .title("title")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(0)
                        .languages("C, JAVA")
                        .access(Access.ADMIN)
                        .field(Field.AI)
                        .fileName("file")
                        .build()
        ).getReportId();
    }

    private Integer writeReportAdmin() {
        return reportRepository.save(
                Report.builder()
                        .reportId(2)
                        .title("title")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(1)
                        .languages("C, JAVA")
                        .access(Access.ADMIN)
                        .field(Field.WEB)
                        .fileName("file")
                        .build()
        ).getReportId();
    }

    private Integer writeReportEvery() {
        return reportRepository.save(
                Report.builder()
                        .reportId(3)
                        .title("title")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(1)
                        .languages("C, JAVA")
                        .access(Access.ADMIN)
                        .field(Field.APP)
                        .fileName("file")
                        .build()
        ).getReportId();
    }

    private Integer writeReportEvery2() {
        return reportRepository.save(
                Report.builder()
                        .reportId(4)
                        .title("title")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(2)
                        .languages("C, JAVA")
                        .access(Access.ADMIN)
                        .field(Field.EMBEDDED)
                        .fileName("file")
                        .build()
        ).getReportId();
    }

    private Integer createTeam(Integer reportId) {
        return teamRepository.save(
                Team.builder()
                        .name("랄랄라")
                        .reportId(reportId)
                        .userEmail("test@dsm.hs.kr")
                        .build()
        ).getId();
    }*/
}
