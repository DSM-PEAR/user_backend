package com.dsmpear.main.entity.userreport;

import com.dsmpear.main.MainApplication;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.verifynumber.VerifyNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@ActiveProfiles({"test"})
public class UserReportRepositoryTest {

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    private UserReport userReport;

    private User user;

    private Report report;

    @BeforeEach
    public void setup() throws Exception {
        user = userRepository.save(
                User.builder()
                        .authStatus(true)
                        .email("email")
                        .name("name")
                        .password("pwd")
                        .selfIntro("aa")
                        .gitHub("sss")
                        .build()
        );

        boolean isSubmitted = false;
        for(int i = 0; i < 10; i++) {
            if(i % 2 == 0) {
                isSubmitted = true;
            }
            report = reportRepository.save(
                    Report.builder()
                            .title("title"+i)
                            .description("111")
                            .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                            .grade(Grade.GRADE1)
                            .access(Access.EVERY)
                            .field(Field.WEB)
                            .type(Type.TEAM)
                            .isAccepted(true)
                            .isSubmitted(isSubmitted)
                            .comment(null)
                            .fileName("파아아일")
                            .github("기이이잇허브")
                            .languages("어어너ㅓㅓㅓ너ㅓ")
                            .teamName("asdf")
                            .build()
            );
            isSubmitted = false;
            userReport = userReportRepository.save(
                    UserReport.builder()
                            .report(report)
                            .user(user)
                            .build()
            );
        }



    }

    @AfterEach
    public void cleanUp() {
        userReportRepository.deleteAll();
        userRepository.deleteAll();
        reportRepository.deleteAll();
    }

    @Test
    public void userReportFindTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserReport> page = userReportRepository.findAllByUserAndReportIsAcceptedTrueAndReportIsSubmittedTrueAndReportAccessOrderByReportCreatedAtDesc(user, Access.EVERY, pageable);

        Assertions.assertEquals(page.getTotalElements(), 5L);
    }
}
