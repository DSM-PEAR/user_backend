package com.dsmpear.main.service.report;

import com.dsmpear.main.entity.comment.Comment;
import com.dsmpear.main.entity.comment.CommentRepository;
import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.userreport.UserReport;
import com.dsmpear.main.entity.userreport.UserReportRepository;
import com.dsmpear.main.exceptions.PermissionDeniedException;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ReportCommentsResponse;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.payload.response.ReportResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import com.dsmpear.main.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final UserReportRepository userReportRepository;

    // 보고서 작성
    @Override
    @Transactional
    public void writeReport(ReportRequest reportRequest) {
        if(authenticationFacade.isLogin() == false) {
            throw new UserNotFoundException();
        }
        String teamName = reportRequest.getTeamName().equals("")?
                userRepository.findByEmail(authenticationFacade.getUserEmail()).get().getName()
                :reportRequest.getTeamName();

        Report report = reportRepository.save(
                Report.builder()
                        .title(reportRequest.getTitle())
                        .description(reportRequest.getDescription())
                        .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .grade(reportRequest.getGrade())
                        .access(reportRequest.getAccess())
                        .field(reportRequest.getField())
                        .type(reportRequest.getType())
                        .accepted(1)
                        .isSubmitted(reportRequest.getIsSubmitted())
                        .fileName(reportRequest.getFileName())
                        .github(reportRequest.getGithub())
                        .languages(reportRequest.getLanguages())
                        .teamName(teamName)
                        .build()
        );


        memberRepository.save(
            Member.builder()
                    .reportId(report.getReportId())
                    .userEmail(authenticationFacade.getUserEmail())
                    .build()
        );

        userReportRepository.save(
                UserReport.builder()
                    .userEmail(authenticationFacade.getUserEmail())
                    .reportId(report.getReportId())
                    .build()
        );

    }

    // 보고서 보기
    @Override
    public ReportContentResponse viewReport(Integer reportId) {
        boolean isMine = false;
        // 이메일 받아오기. 없으면 null이 들어갈껄?

        boolean isLogined = authenticationFacade.isLogin();

        // 보고서를 아이디로 찾기
        Report report = reportRepository.findByReportId(reportId)
                .orElseThrow(ReportNotFoundException::new);

        List<Member> members = report.getMembers();

        if(isLogined) {
            for (Member member : members) {
                if (member.getUserEmail().equals(authenticationFacade.getUserEmail())) {
                    isMine = true;
                    break;
                }
            }

            // 보고서를 볼 때 보는 보고서의 access가 ADMIN인지, 만약 admin이라면  현재 유저가 글쓴이가 맞는지 검사
            if (!isMine) {
                if (!(report.getAccess().equals(Access.EVERY))) {
                    throw new PermissionDeniedException();
                }else if(report.getAccepted() != 2 || !report.getIsSubmitted()) {
                    throw new PermissionDeniedException();
                }
            }
        }else {
            if(report.getAccess().equals(Access.ADMIN)) {
                throw new PermissionDeniedException();
            }
        }

        List<Comment> comment = commentRepository.findAllByReportIdOrderByCreatedAtAsc(reportId);
        List<ReportCommentsResponse> commentsResponses = new ArrayList<>();

        // 댓글 하나하나 담기ㅣ
        for (Comment co : comment) {
            User commentWriter;
            commentWriter = userRepository.findByEmail(co.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            commentsResponses.add(
                    ReportCommentsResponse.builder()
                            .content(co.getContent())
                            .createdAt(co.getCreatedAt())
                            .userEmail(co.getUserEmail())
                            .userName(userRepository.findByEmail(co.getUserEmail()).get().getName())
                            .isMine(commentWriter.getEmail().equals(authenticationFacade.getUserEmail()))
                            .build()
            );
        }

        return ReportContentResponse.builder()
                .access(report.getAccess())
                .grade(report.getGrade())
                .type(report.getType())
                .field(report.getField())
                .languages(report.getLanguages())
                .title(report.getTitle())
                .fileName(report.getFileName())
                .createdAt(report.getCreatedAt())
                .description(report.getDescription())
                .isMine(isMine)
                .comments(commentsResponses)
                .build();
    }

    @Override
    public Integer updateReport(Integer reportId, ReportRequest reportRequest) {

        boolean isMine = false;

        if(authenticationFacade.isLogin()) {
            memberRepository.findByReportIdAndUserEmail(reportId, authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }else {
            throw new UserNotFoundException();
        }

        Report report = reportRepository.findByReportId(reportId).
                orElseThrow(ReportNotFoundException::new);

        reportRepository.save(report.update(reportRequest));

        return reportId;
    }

    @Override
    public void deleteReport(Integer reportId) {
        boolean isMine = false;
        User user = null;
        if(authenticationFacade.isLogin()) {
            user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            memberRepository.findByReportIdAndUserEmail(reportId, authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }else {
            throw new UserNotFoundException();
        }

        UserReport userReport = userReportRepository.findByReportIdAndUserEmail(reportId,user.getEmail())
                .orElseThrow(ReportNotFoundException::new);

        Report report = reportRepository.findByReportId(reportId)
                .orElseThrow(ReportNotFoundException::new);

        for(Comment comment : commentRepository.findAllByReportIdOrderByCreatedAtAsc(reportId)) {
            commentService.deleteComment(comment.getId());
        }

        for(Member member : report.getMembers()) {
            memberRepository.deleteById(member.getId());
        }

        userReportRepository.deleteAllByReportId(reportId);

        reportRepository.deleteById(reportId);
    }

    @Override
    public ReportListResponse getReportList(Pageable page, Type type, Field field, Grade grade) {
        boolean isLogined = authenticationFacade.isLogin();
        User user = null;

        if (isLogined) {
            user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }

        List<ReportResponse> reportResponses = new ArrayList<>();
        Page<Report> reportPage;

        if(type == null && field == null) {
            reportPage = reportRepository.findAllByAccessAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access.EVERY, grade, 2, page);
        }else if(type == null) {
            reportPage = reportRepository.findAllByAccessAndFieldAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access.EVERY, field, grade, 2, page);
        }else if(field == null) {
            reportPage = reportRepository.findAllByAccessAndTypeAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access.EVERY, type, grade, 2, page);
        }else {
            reportPage = reportRepository.findAllByAccessAndFieldAndTypeAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAt(Access.EVERY, field, type, grade, 2, page);
        }

        for(Report report : reportPage) {
            reportResponses.add(
                    ReportResponse.builder()
                            .reportId(report.getReportId())
                            .title(report.getTitle())
                            .createdAt(report.getCreatedAt())
                            .build()
            );
        }

        return ReportListResponse.builder()
                .totalElements((int) reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .reportResponses(reportResponses)
                .build();
    }

}