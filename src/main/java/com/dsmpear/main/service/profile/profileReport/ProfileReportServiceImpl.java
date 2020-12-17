package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.entity.report.Access;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.userreport.UserReport;
import com.dsmpear.main.entity.userreport.UserReportRepository;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.response.MyPageReportResponse;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.payload.response.ProfileReportResponse;
import com.dsmpear.main.payload.response.ReportResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileReportServiceImpl implements ProfileReportService{

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final UserReportRepository userReportRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public ProfileReportListResponse getReport(String userEmail, Pageable page) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        Page<UserReport> reportPage = userReportRepository.findAllByUserEmail(user.getEmail(), page);

        List<ProfileReportResponse> profileReportResponses = new ArrayList<>();

        /*for(UserReport userReport : reportPage){
            Report report = reportRepository.findAllByAccessAndIsAccepted(Access.EVERY, true)
                    .orElseThrow(ReportNotFoundException::new);

            profileReportResponses.add(
                    ProfileReportResponse.builder()
                            .reportId(report.getReportId())
                            .title(report.getTitle())
                            .createdAt(report.getCreatedAt())
                            .build()
            );
        }*/
        return ProfileReportListResponse.builder()
                .totalElements(reportPage.getNumberOfElements())
                .totalPages(reportPage.getTotalPages())
                .profileReportResponses(profileReportResponses)
                .build();
    }

}