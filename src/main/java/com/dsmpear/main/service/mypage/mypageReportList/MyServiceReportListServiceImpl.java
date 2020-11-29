package com.dsmpear.main.service.mypage.mypageReportList;

import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.TeamNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.exceptions.UserNotMemberException;
import com.dsmpear.main.payload.response.ApplicationListResponse;
import com.dsmpear.main.payload.response.MyProfileListResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyServiceReportListServiceImpl implements MyPageReportListService{

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final AuthenticationFacade authenticationFacade;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;


    @Override
    public ApplicationListResponse getReport(Pageable page) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);


        Page<Report> myProfilePage = reportRepository.findAllBy(page);

        List<MyProfileListResponse> myProfileListResponses = new ArrayList<>();


        for(Report report : myProfilePage){
            Team team = teamRepository.findByReportId(report.getReportId())
                    .orElseThrow(TeamNotFoundException::new);

            memberRepository.findByTeamIdAndUserEmail(report.getReportId(),user.getEmail())
                    .orElseThrow(UserNotMemberException::new);

            myProfileListResponses.add(
                    MyProfileListResponse.builder()
                            .reportId(report.getReportId())
                            .title(report.getTitle())
                            .teamName(team.getName())
                            .isAccepted(report.getIsAccepted())
                            .createdAt(report.getCreatedAt())
                            .build()
            );
        }

        return ApplicationListResponse.builder()
                .totalElements((int) myProfilePage.getTotalElements())
                .totalPages(myProfilePage.getTotalPages())
                .applicationResponses(myProfileListResponses)
                .build();
    }


}
