
package com.dsmpear.main.domain;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.config.ObjectMapperConfiguration;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.payload.response.SearchProfileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles("test")
class SearchControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapperConfiguration objectMapperConfiguration;

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

        createReport("제목1호 please....!!!",
                "내용1호",
                Access.EVERY,
                true);

        createReport("제목2호 please....!!!",
                "내용2호",
                Access.EVERY,
                true);

        createReport("제목3호 이건 보겠지",
                "내용3호",
                Access.EVERY,
                true);

        createReport("제목4호 이건 못보겠지",
                "내용4호",
                Access.EVERY,
                true);

        createReport("제목5호 이건 못보겠지",
                "내용5호",
                Access.ADMIN,
                true);

    }

    @AfterEach
    public void after () {
        userRepository.deleteAll();
        reportRepository.deleteAll();
    }

    @Test
    public void searchProfile () throws Exception {
        mvc.perform(get("/search/profile?keyword=길동&size=10&page=0")).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void searchProfile_noKeyword () throws Exception {
        mvc.perform(get("/search/profile?keyword=&size=10&page=0")).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void searchProfile_full () throws Exception {
        mvc.perform(get("/search/profile?keyword=이길동&size=10&page=0")).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void searchProfile_notFound () throws Exception {
        MvcResult result = mvc.perform(get("/search/profile?keyword=동글이&size=10&page=0")).andReturn();

        SearchProfileResponse response = new ObjectMapper().readValue(result.getResponse().getContentAsString(), SearchProfileResponse.class);
        Assert.assertEquals(response.getTotalElements(), 0);
    }

    @Test
    public void searchReportByTitle() throws Exception {
        MvcResult result = mvc.perform(get("/search/report?keyword=please....!!!&size=10&page=0")).andReturn();
        ReportListResponse response = objectMapperConfiguration.objectMapper().readValue(result.getResponse().getContentAsString(), ReportListResponse.class);
        Assert.assertEquals(2, response.getTotalElements());
    }

    @Test
    public void searchReportNoKeyword() throws Exception {
        MvcResult result = mvc.perform(get("/search/report?keyword=&size=10&page=0")).andReturn();
        ReportListResponse response = objectMapperConfiguration.objectMapper().readValue(result.getResponse().getContentAsString(), ReportListResponse.class);
        Assert.assertEquals(response.getTotalElements(), 4);
    }

    @Test
    public void searchReportNoResult_NoTitle () throws Exception {
        MvcResult result = mvc.perform(get("/search/report?keyword=please...!!!&size=10&page=0")).andReturn();
        ReportListResponse response = objectMapperConfiguration.objectMapper().readValue(result.getResponse().getContentAsString(), ReportListResponse.class);
        Assert.assertEquals(response.getTotalElements(), 0);
    }

    @Test
    public void searchReportNoResult_NoPermission () throws Exception {
        MvcResult result = mvc.perform(get("/search/report?keyword=제목4호&size=10&page=0")).andReturn();
        ReportListResponse response = objectMapperConfiguration.objectMapper().readValue(result.getResponse().getContentAsString(), ReportListResponse.class);
        Assert.assertEquals(response.getTotalElements(), 1);
    }

    @Test
    public void searchReportNoResult_NotAccepted () throws Exception {
        MvcResult result = mvc.perform(get("/search/report?keyword=제목5호&size=10&page=0")).andReturn();
        ReportListResponse response = objectMapperConfiguration.objectMapper().readValue(result.getResponse().getContentAsString(), ReportListResponse.class);
        Assert.assertEquals(response.getTotalElements(), 0);
    }

    void createReport(String title, String description, Access access, Boolean isAccepted) {
        reportRepository.save(
                Report.builder()
                .title(title)
                .description(description)
                .access(access)
                .createdAt(LocalDateTime.now())
                .field(Field.AI)
                .fileName("dasf")
                .github("깃허브!@!")
                .grade(Grade.GRADE2)
                .isAccepted(isAccepted)
                .isSubmitted(true)
                .languages("언어다ㅏ")
                .type(Type.TEAM)
                .teamName("팀이름이다아ㅏ")
                .build()
        );
    }
}