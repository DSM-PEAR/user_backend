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
import com.dsmpear.main.payload.response.*;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import com.dsmpear.main.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final UserReportRepository userReportRepository;

    // 보고서 작성
    @Override
    @Transactional
    public Integer writeReport(ReportRequest reportRequest) {
        if(!authenticationFacade.isLogin()) {
            throw new UserNotFoundException();
        }

        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        String teamName = reportRequest.getTeamName().equals("")?
                user.getName():reportRequest.getTeamName();

        Report report = reportRepository.save(
                Report.builder()
                        .title(reportRequest.getTitle())
                        .description(reportRequest.getDescription())
                        .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .grade(reportRequest.getGrade())
                        .access(reportRequest.getAccess())
                        .field(reportRequest.getField())
                        .type(reportRequest.getType())
                        .isAccepted(false)
                        .isSubmitted(reportRequest.getIsSubmitted())
                        .comment(null)
                        .fileName(reportRequest.getFileName())
                        .github(reportRequest.getGithub())
                        .languages(reportRequest.getLanguages())
                        .teamName(teamName)
                        .build()
        );

        memberRepository.save(
            Member.builder()
                    .report(report)
                    .userEmail(user.getEmail())
                    .build()
        );

        userReportRepository.save(
                UserReport.builder()
                    .report(report)
                    .user(user)
                    .build()
        );

        return report.getId();
    }

    // 보고서 보기
    @Override
    public ReportContentResponse viewReport(Integer reportId) {
        boolean isMine = false;
        // 이메일 받아오기. 없으면 null이 들어갈껄?

        boolean isLogined = authenticationFacade.isLogin();

        // 보고서를 아이디로 찾기
        Report report = reportRepository.findById(reportId)
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
                if (!(report.getAccess().equals(Access.EVERY)) ||
                !report.getIsAccepted() || !report.getIsSubmitted()) {
                    throw new PermissionDeniedException();
                }
            }
        }else {
            if(report.getAccess().equals(Access.ADMIN)) {
                throw new PermissionDeniedException();
            }
        }

        List<Comment> comment = commentRepository.findAllByReportIdOrderByCreatedAtDesc(reportId);
        List<ReportCommentsResponse> commentsResponses = new ArrayList<>();

        // 댓글 하나하나 담기
        for (Comment co : comment) {
            User commentWriter;
            commentWriter = userRepository.findByEmail(co.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
            commentsResponses.add(
                    ReportCommentsResponse.builder()
                            .content(co.getContent())
                            .createdAt(co.getCreatedAt())
                            .userEmail(co.getUserEmail())
                            .userName(commentWriter.getName())
                            .isMine(commentWriter.getEmail().equals(authenticationFacade.getUserEmail()))
                            .commentId(co.getId())
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
                .teamName(report.getTeamName())
                .comment(report.getComment())
                .member(getMember(report).getMemberResponses())
                .fileId(report.getReportFile() == null ? null : report.getReportFile().getId())
                .build();
    }

    @Override
    public Integer updateReport(Integer reportId, ReportRequest reportRequest) {
        if(!authenticationFacade.isLogin()) {
            throw new UserNotFoundException();
        }

        Report report = reportRepository.findById(reportId).
                orElseThrow(ReportNotFoundException::new);

        memberRepository.findByReportAndUserEmail(report, authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        reportRepository.save(report.update(reportRequest));

        return reportId;
    }

    @Override
    @Transactional
    public void deleteReport(Integer reportId) {
        if(!authenticationFacade.isLogin()) {
            throw new UserNotFoundException();
        }

        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);

        memberRepository.findByReportAndUserEmail(report, user.getEmail())
                .orElseThrow(UserNotFoundException::new);

        commentRepository.deleteAllByReportId(reportId);

//        memberRepository.deleteAllByReport(report);

//        userReportRepository.deleteAllByReportId(reportId);

        reportRepository.deleteById(reportId);
    }

    @Override
    public ReportListResponse getReportList(Pageable page, Type type, Field field, Grade grade) {

        List<ReportResponse> reportResponses = new ArrayList<>();
        Page<Report> reportPage = reportRepository.findAllByAccessAndGradeAndFieldAndType(Access.EVERY, grade, field, type, page);

        for(Report report : reportPage) {
            reportResponses.add(
                    ReportResponse.builder()
                            .reportId(report.getId())
                            .title(report.getTitle())
                            .createdAt(report.getCreatedAt())
                            .type(report.getType())
                            .build()
            );
        }

        return ReportListResponse.builder()
                .totalElements(reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .reportResponses(reportResponses)
                .build();
    }

    private MemberListResponse getMember(Report report) {
        List<Member> memberPage = memberRepository.findAllByReport(report);

        List<MemberResponse> memberResponses = new ArrayList<>();

        reportRepository.findById(report.getId())
                .orElseThrow(ReportNotFoundException::new);

        for(Member member:memberPage){
            memberResponses.add(
                    MemberResponse.builder()
                            .memberId(member.getId())
                            .memberEmail(member.getUserEmail())
                            .memberName(userRepository.findByEmail(member.getUserEmail()).get().getName())
                            .build()
            );
        }

        return MemberListResponse.builder()
                .memberResponses(memberResponses)
                .build();
    }

}