package com.dsmpear.main.service.report;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Access;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.*;
import com.dsmpear.main.payload.request.CreateReportRequest;
import com.dsmpear.main.payload.response.ReportCommentsResponse;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;


    // 보고서 작성
    @Override
    public void writeReport(CreateReportRequest createReportRequest) {
        reportRepository.save(
                Report.builder()
                        .title(createReportRequest.getTitle())
                        .description(createReportRequest.getDescription())
                        .languages(createReportRequest.getLanguages())
                        .type(createReportRequest.getType())
                        .access(createReportRequest.getAccess())
                        .grade(createReportRequest.getGrade())
                        .build()
        );
    }

    // 보고서 보기
    @Override
    public ReportContentResponse viewReport(Integer reportId) {
        // (드디어) 토큰에서 유저 가져오기
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        // 보고서를 아이디로 찾기
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);

        // 보고서의 팀을 받아오자ㅏㅏ
        Team team = teamRepository.findByReportId(reportId)
                .orElseThrow(TeamNotFoundException::new);

        boolean isMine = !memberRepository.findByTeamIdAndUserEmail(team.getId(), user.getEmail()).isEmpty();
        // 보고서를 볼 때 보는 보고서의 access가 ADMIN인지, 만약 admin이라면  현재 유저가 글쓴이가 맞는지 검사
        if (report.getAccess().equals(Access.ADMIN) && !isMine) {
            throw new PermissionDeniedException();
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
                .comments()
                .build();
    }

}
