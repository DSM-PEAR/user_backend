package com.dsmpear.main.domain;

import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import org.junit.After;
import org.junit.Test;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReportRepository reportRepository;

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
                        .selfIntro("")
                        .authStatus(true)
                        .build()
        );
    }
    @After
    public void deleteAll(){
        reportRepository.deleteAll();;
    }

    @Test
    public void getEveryAccess() throws Exception{

        createReport();
        createEveryReport();
        createUserReport();

        mvc.perform(get("/profile/report")
                .param("size","5")
                .param("page","2")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @WithMockUser(value = "test@dsm.hs.kr",password = "1111")
    public void getUserAccess() throws Exception{

        createReport();
        createEveryReport();
        createUserReport();

        mvc.perform(get("/profile/report")
                .param("size","5")
                .param("page","2")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andDo(print());
    }

    private void createEveryReport(){
        reportRepository.save(
                Report.builder()
                        .reportId(1)
                        .title("everyTitle")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(1)
                        .languages("C, JAVA")
                        .access(Access.EVERY)
                        .build()
        );
    }
    private void createUserReport(){
        reportRepository.save(
                Report.builder()
                        .reportId(2)
                        .title("userTitle")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(1)
                        .languages("C, JAVA")
                        .access(Access.USER)
                        .build()
        );
    }
    private void createReport(){
        reportRepository.save(
                Report.builder()
                        .reportId(3)
                        .title("adminTitle")
                        .description("description")
                        .createdAt(LocalDateTime.now())
                        .type(Type.TEAM)
                        .grade(Grade.GRADE1)
                        .isAccepted(1)
                        .languages("C, JAVA")
                        .access(Access.ADMIN)
                        .build()
        );
    }

}
