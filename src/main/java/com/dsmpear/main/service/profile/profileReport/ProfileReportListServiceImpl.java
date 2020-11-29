package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.exceptions.UserNotMemberException;
import com.dsmpear.main.payload.response.ApplicationListResponse;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
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


    @Override
    public ApplicationListResponse getReport(String email, Pageable page) {

        //로그인을 했으면 학생 공개까지, 로그인이 안되어있다면 전체공개만
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Page<Report> profileReportPage = reportRepository.findAllBy(page);

        List<ProfileReportListResponse> profileReportListResponses = new ArrayList<>();

        for(Report report : profileReportPage){
            memberRepository.findByTeamIdAndUserEmail(report.getReportId(),email)
                    .orElseThrow(UserNotMemberException::new);

            profileReportListResponses.add(
                    ProfileReportListResponse.builder()
                            .reportId(report.getReportId())
                            .title(report.getTitle())
                            .isAccepted(report.getIsAccepted())
                            .createdAt(report.getCreatedAt())
                            .build()
            );
        }

        return ApplicationListResponse.builder()
                .totalElements((int) profileReportPage.getTotalElements())
                .totalPages(profileReportPage.getTotalPages())
                .applicationResponses(profileReportListResponses)
                .build();
    }
}
