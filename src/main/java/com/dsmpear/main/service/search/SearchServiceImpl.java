package com.dsmpear.main.service.search;

import com.dsmpear.main.entity.report.Access;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.payload.response.ReportResponse;
import com.dsmpear.main.payload.response.SearchProfileResponse;
import com.dsmpear.main.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Override
    public SearchProfileResponse searchProfile(String keyword, Pageable page) {

        List<UserResponse> userResponses = new ArrayList<>();

        Page<User> userPage = userRepository.findAllByNameContains(keyword, page);

        for (User user : userPage) {
            userResponses.add(
                    UserResponse.builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .build()
            );
        }

        return SearchProfileResponse.builder()
                .totalElements(userPage.getNumberOfElements())
                .totalPages(userPage.getTotalPages())
                .userResponses(userResponses)
                .build();
    }

    @Override
    public ReportListResponse searchReportByTitle(Pageable page, String title) {
        Page<Report> reportPage = reportRepository.findAllByAccessAndIsAcceptedAndIsSubmittedTrueAndTitleContainingOrderByCreatedAtDesc(Access.EVERY, title, 2, page);

        List<ReportResponse> reportResponses = new ArrayList<>();

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
