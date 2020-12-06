/*
package com.dsmpear.main.domain;

import com.dsmpear.main.entity.comment.Comment;
import com.dsmpear.main.entity.comment.CommentRepository;
import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.MemberNotFoundException;
import com.dsmpear.main.exceptions.TeamNotFoundException;
import com.dsmpear.main.payload.request.CommentRequest;
import com.dsmpear.main.payload.request.ReportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
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
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReportControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mvc;

    @Before
    public void setUp() {
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
                        .email("test1@dsm.hs.kr")
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

    // 보고서 작성 성공 테스트
    @Test
    @Order(1)
    @WithMockUser(value = "test@dsm.hs.kr",password="1234")
    public void createReportTest() throws Exception {

        ReportRequest request = ReportRequest.builder()
                .title("1. 이승윤 돼지")
                .description("내애용은 이승윤 돼지")
                .grade(Grade.GRADE2)
                .access(Access.EVERY)
                .field(Field.AI)
                .type(Type.TEAM)
                .isAccepted(0)
                .languages("자바")
                .fileName("이승윤 돼지")
                .build();

        mvc.perform(post("/report")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    // 보고서 작성 실패 테스트(UserNotFound)
    @Test
    @Order(1)
    */
/*@WithMockUser(value = "test3@dsm.hs.kr",password="1234")*//*

    public void createReportTest2() throws Exception {

        ReportRequest request = ReportRequest.builder()
                .title("1. 이승윤 돼지")
                .description("내애용은 이승윤 돼지")
                .grade(Grade.GRADE2)
                .access(Access.EVERY)
                .field(Field.AI)
                .type(Type.TEAM)
                .isAccepted(0)
                .languages("자바")
                .fileName("이승윤 돼지")
                .build();

        mvc.perform(post("/report")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    // 보고서 작성 실패 테스트(InvalidData인데 에러메세지는 없음)
    @Test
    @Order(1)
    @WithMockUser(value = "test@dsm.hs.kr",password="1234")
    public void createReportTest3() throws Exception {

        ReportRequest request = ReportRequest.builder()
                .title("")
                .description("내애용은 이승윤 돼지")
                .grade(Grade.GRADE2)
                .access(Access.EVERY)
                .field(Field.AI)
                .type(Type.TEAM)
                .isAccepted(0)
                .languages("자바")
                .fileName("이승윤 돼지")
                .build();

        mvc.perform(post("/report")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    // 보고서 보기 성공 테스트
    @Test
    @Order(1)
    @WithMockUser(value = "test@dsm.hs.kr",password="1234")
    public void getReportTest() throws Exception {

        Integer reportId = createReport();

        Integer teamId = createTeam(reportId);

        Integer memberId1 = addMember(teamId);

        mvc.perform(get("/report/"+reportId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 보고서 보기 성공 테스트(EVERY꺼)
    @Test
    @Order(1)
    */
/*@WithMockUser(value = "test1@dsm.hs.kr",password="1234")*//*

    public void getReportTest1() throws Exception {

        Integer reportId = createReport();

        Integer teamId = createTeam(reportId);

        Integer memberId1 = addMember(teamId);

        mvc.perform(get("/report/"+reportId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @Order(1)
    public void getReportTest2() throws Exception {

        Integer reportId = createReport();

        Integer teamId = createTeam(reportId);

        Integer memberId1 = addMember(teamId);

        mvc.perform(get("/report/"+reportId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 보고서 업데이트 성공 테스트
    @Test
    @Order(1)
    @WithMockUser(value = "test@dsm.hs.kr",password="1234")
    public void updateReportTest() throws Exception {

        Integer reportId = createReport();

        Integer teamId = createTeam(reportId);

        addMember(teamId);

        ReportRequest request = ReportRequest.builder()
                .title("2. 이승윤 돼지")
                .description("2째 돼지 이승윤")
                .languages("돼지")
                .type(Type.TEAM)
                .access(Access.USER)
                .grade(Grade.GRADE1)
                .field(Field.AI)
                .fileName("돼지")
                .isAccepted(1)
                .build();



        mvc.perform(patch("/report/"+reportId)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 보고서 업데이트 실패 테스트(userNotMemer)
    @Test
    @Order(1)
    @WithMockUser(value = "test1@dsm.hs.kr",password="1234")
    public void updateReportTest1() throws Exception {

        Integer reportId = createReport();

        ReportRequest request = ReportRequest.builder()
                .title("2. 이승윤 돼지")
                .description("2째 돼지 이승윤")
                .languages("돼지")
                .type(Type.TEAM)
                .access(Access.USER)
                .grade(Grade.GRADE1)
                .field(Field.AI)
                .fileName("돼지")
                .isAccepted(1)
                .build();

        createTeam(reportId);

        mvc.perform(patch("/report/"+reportId)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 보고서 업데이트 실패 테스트(UserNotFound)
    @Test
    @Order(1)
    public void updateReportTest2() throws Exception {

        Integer reportId = createReport();

        ReportRequest request = ReportRequest.builder()
                .title("2. 이승윤 돼지")
                .description("2째 돼지 이승윤")
                .languages("돼지")
                .type(Type.TEAM)
                .access(Access.USER)
                .grade(Grade.GRADE1)
                .field(Field.AI)
                .fileName("돼지")
                .isAccepted(1)
                .build();

        createTeam(reportId);

        mvc.perform(patch("/report/"+reportId)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 보고서 삭제 성공 테스트
    @Test
    @Order(1)
    @WithMockUser(value = "test@dsm.hs.kr",password="1234")
    public void deleteReportTest() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));

        mvc.perform(delete("/report/"+reportId)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 보고서 삭제 실패 테스트
    @Test
    @Order(1)
    @WithMockUser(value = "test12@dsm.hs.kr",password="1234")
    public void deleteReportTest1() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));

        mvc.perform(delete("/report/"+reportId)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 보고서 삭제 실패 테스트
    @Test
    @Order(1)
    public void deleteReportTest2() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));

        mvc.perform(delete("/report/"+reportId)).andDo(print())
                .andExpect(status().isOk()).andDo(print());
    }

    // 댓글 작성 성공 테스트
    @Test
    @Order(2)
    @WithMockUser(value = "test@dsm.hs.kr", password = "1234")
    public void createComment() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);

        CommentRequest request = CommentRequest.builder()
                .reportId(reportId)
                .userEmail("test@dsm.hs.kr")
                .content("아이야아이야")
                .build();

        mvc.perform(post("/comment")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    // 댓글 작성 실패 테스트
    @Test
    @Order(2)
    public void createComment1() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);

        CommentRequest request = CommentRequest.builder()
                .reportId(reportId)
                .userEmail("test@dsm.hs.kr")
                .content("아이야아이야")
                .build();

        mvc.perform(post("/comment")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    // 댓글 수정 성공 테스트
    @Test
    @Order(2)
    @WithMockUser(value = "test@dsm.hs.kr", password = "1234")
    public void updateComment() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);
        Integer commentId2 = createComment(reportId);

        mvc.perform(patch("/comment/"+commentId1)
                .param("content", "content")).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    // 댓글 수정 성공 테스트
    @Test
    @Order(2)
    @WithMockUser(value = "test22@dsm.hs.kr", password = "1234")
    public void updateComment2() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);
        Integer commentId2 = createComment(reportId);

        mvc.perform(patch("/comment/"+commentId1)
                .param("content", "content")).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }


    // 댓글 수정 실패 테스트
    @Test
    @Order(2)
    public void updateComment1() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);
        Integer commentId2 = createComment(reportId);

        mvc.perform(patch("/comment/"+commentId1)
                .param("content", "content")).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }

    // 댓글 삭제 성공 테스트
    @Test
    @Order(2)
    @WithMockUser(value = "test@dsm.hs.kr", password = "1234")
    public void deleteComment() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);
        Integer commentId2 = createComment(reportId);

        mvc.perform(delete("/comment/"+commentId1)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }


    // 댓글 삭제 성공 테스트
    @Test
    @Order(2)
    @WithMockUser(value = "test1@dsm.hs.kr", password = "1234")
    public void deleteComment1() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);
        Integer commentId2 = createComment(reportId);

        mvc.perform(delete("/comment/"+commentId1)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

    }


    // 댓글 삭제 성공 테스트
    @Test
    @Order(2)
    public void deleteComment2() throws Exception {
        Integer reportId = createReport();
        addMember(createTeam(reportId));
        Integer commentId1 = createComment(reportId);
        Integer commentId2 = createComment(reportId);

        mvc.perform(delete("/comment/"+commentId1)).andDo(print())
                .andExpect(status().isOk()).andDo(print());

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

    private Integer createReport() throws Exception {
        return reportRepository.save(
                Report.builder()
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .title("1. 이승윤 돼지")
                .description("내애용은 이승윤 돼지")
                .languages("자바")
                .type(Type.TEAM)
                .access(Access.USER)
                .grade(Grade.GRADE2)
                .isAccepted(0)
                .field(Field.AI)
                .fileName("이승윤 돼지")
                .isAccepted(0)
                .build()
        ).getReportId();
    }

    private Integer createComment(Integer reportId) throws Exception {
        return commentRepository.save(
                Comment.builder()
                .reportId(reportId)
                .userEmail("test@dsm.hs.kr")
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .content("아이야아이야")
                .build()
        ).getId();
    }



}*/
