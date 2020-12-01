package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.TeamNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.exceptions.UserNotMemberException;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.payload.response.ProfileReportResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileReportListServiceImpl implements ProfileReportListService{

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final AuthenticationFacade authenticationFacade;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;


    @Override
    public ProfileReportListResponse getReport(String email, Pageable page) {

        //로그인을 했으면 학생 공개까지, 로그인이 안되어있다면 전체공개만
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Page<Report> profileReportPage = reportRepository.findAllBy(page);

        List<ProfileReportResponse> profileReportListResponses = new ArrayList<>();

        for(Report report : profileReportPage){
            Team team = teamRepository.findByReportId(report.getReportId())
                    .orElseThrow(TeamNotFoundException::new);

            memberRepository.findByTeamIdAndUserEmail(report.getReportId(),email)
                    .orElseThrow(UserNotMemberException::new);

            profileReportListResponses.add(
                    ProfileReportResponse.builder()
                            .reportId(report.getReportId())
                            .title(report.getTitle())
                            .teamName(team.getName())
                            .isAccepted(report.getIsAccepted())
                            .createdAt(report.getCreatedAt())
                            .build()
            );
        }

        return ProfileReportListResponse.builder()
                .totalElements((int) profileReportPage.getTotalElements())
                .totalPages(profileReportPage.getTotalPages())
                .profileReportResponses(profileReportListResponses)
                .build();
    }
}
