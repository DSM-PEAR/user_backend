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
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.payload.response.ProfileReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileReportServiceImpl implements ProfileReportService {

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;

    @Override
    public ProfileReportListResponse getReport(String userEmail, Pageable page) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        Page<UserReport> userReportPage = userReportRepository.findAllByUserAndReportIsAcceptedTrueAndReportIsSubmittedTrueAndReportAccessOrderByReportCreatedAtDesc(user, Access.EVERY, page);
        List<ProfileReportResponse> profileReportResponses = new ArrayList<>();

        for(UserReport userReport : userReportPage){
            Report report = userReport.getReport();
            
            profileReportResponses.add(
                    ProfileReportResponse.builder()
                            .reportId(report.getId())
                            .title(report.getTitle())
                            .type(userReport.getReport().getType())
                            .createdAt(report.getCreatedAt())
                            .build()
            );
        }

        return ProfileReportListResponse.builder()
                .totalElements(userReportPage.getTotalElements())
                .totalPages(userReportPage.getTotalPages())
                .profileReportResponses(profileReportResponses)
                .build();
    }

}