package com.dsmpear.main.service.report;

import com.dsmpear.main.entity.comment.Comment;
import com.dsmpear.main.entity.comment.CommentRepository;
import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.*;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.userreport.UserReportRepository;
import com.dsmpear.main.exceptions.PermissionDeniedException;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ReportCommentsResponse;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import com.dsmpear.main.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private final CommentService commentService;

    // 보고서 작성
    @Override
    public void writeReport(ReportRequest reportRequest) {
        if(authenticationFacade.isLogin() == false) {
            throw new UserNotFoundException();
        }
        Report report = reportRepository.save(
                Report.builder()
                        .title(reportRequest.getTitle())
                        .description(reportRequest.getDescription())
                        .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .grade(reportRequest.getGrade())
                        .access(reportRequest.getAccess())
                        .field(reportRequest.getField())
                        .type(reportRequest.getType())
                        .isAccepted(0)
                        .languages(reportRequest.getLanguages())
                        .fileName(reportRequest.getFileName())
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .reportId(report.getReportId())
                        .userEmail(authenticationFacade.getUserEmail())
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

        // 보고서의 팀을 받아오자ㅏㅏ
        List<Member> members = report.getMembers();

        if(isLogined) {
            for(Member member : members) {
                isMine = !memberRepository.findByReportIdAndUserEmail(reportId, member.getUserEmail()).isEmpty();
            }

            // 보고서를 볼 때 보는 보고서의 access가 ADMIN인지, 만약 admin이라면  현재 유저가 글쓴이가 맞는지 검사
            if (report.getAccess().equals(Access.ADMIN) && !isMine) {
                throw new PermissionDeniedException();
            }
        }else {
            if(report.getAccess().equals(Access.USER)) {
                throw new PermissionDeniedException();
            }
        }

        List<Comment> comment = commentRepository.findAllByReportIdOrderByIdAsc(reportId);
        List<ReportCommentsResponse> commentsResponses = new ArrayList<>();


        // 댓글 하나하나 담기ㅣ
        for (Comment co : comment) {
            User commentWriter;
            if(authenticationFacade.getUserEmail() != null) {
                commentWriter = userRepository.findByEmail(co.getUserEmail())
                        .orElseThrow(UserNotFoundException::new);
            }else {
                throw new PermissionDeniedException();
            }
            commentsResponses.add(
                    ReportCommentsResponse.builder()
                            .content(co.getContent())
                            .createdAt(co.getCreatedAt())
                            .userEmail(co.getUserEmail())
                            .isMine(commentWriter.getEmail().equals(authenticationFacade.getUserEmail()))
                            .build()
            );
        }

        return ReportContentResponse.builder()
                .access(report.getAccess())
                .grade(report.getGrade())
                .type(report.getType())
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
        User user = null;
        boolean isMine = false;
        if(authenticationFacade.isLogin()) {
            memberRepository.findByReportIdAndUserEmail(reportId, authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }else {
            throw new UserNotFoundException();
        }

        Report report = reportRepository.findByReportId(reportId)
                .orElseThrow(ReportNotFoundException::new);

        List<Member> members = report.getMembers();

        for(Comment comment : commentRepository.findAllByReportIdOrderByIdAsc(reportId)) {
            commentService.deleteComment(comment.getId());
        }

        for(Member member : members) {
            memberRepository.deleteById(member.getId());
        }

        reportRepository.deleteById(reportId);
    }

    @Override
    public ReportListResponse getReportList(Pageable page, Field field, Grade grade) {
        boolean isLogined = authenticationFacade.isLogin();
        User user = null;

        if (isLogined) {
            user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }
        page = PageRequest.of(Math.max(0, page.getPageNumber() - 1), page.getPageSize());
        Page<Report> reportPage;

        ReportListResponse lists = null;

        return lists;
//      return reportRepository.findAllByFieldAndGradeAndIsAcceptedAndAccess_UserOrAccess_EveryOrderByCreatedAt(field, grade, 0, Access.EVERY);
    }

    @Override
    public ReportListResponse searchReport(Pageable page, String mode, String query) {
        boolean isLogined= authenticationFacade.getUserEmail() == null;
        ReportListResponse a = null;
        /*
        page = PageRequest.of(Math.max(0, page.getPageNumber()-1), page.getPageSize());
        Page<Report> reportPage;
        switch(mode) {
            case "title":
                reportPage = reportRepository
                        .findAllByTitleContainsOrderByCreatedAt(page,query);
                break;
            case "languages":
                reportPage = reportRepository
                        .findAllByLanguagesContainsOrderByCreatedAt(page, query);
                break;
            default:
                break;
        }
        ApplicationListResponse a = null;*/
        return a;

    }

    @Override
    public ReportListResponse getUserReportList(Pageable page, String email) {
        /*boolean isLogined = authenticationFacade.isLogin();

        if(!isLogined) {
            throw new UserNotFoundException();
        }

        List<Report> userReport = userReportRepository.findAllByUserEmail(email);

        return ReportListResponse.builder()
                .totalElements((int) reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .noticeResponses(noticeResponses)
                .build();*/
        ReportListResponse rep = null;
        return null;
    }
}
