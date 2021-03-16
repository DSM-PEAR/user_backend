package com.dsmpear.main.service.mypage.mypageReport;

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
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageReportServiceImpl implements MyPageReportService{

    private final ReportRepository reportRepository;
    private final UserReportRepository userReportRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    @Override
    public ProfileReportListResponse getReport(Pageable page) {
        if(!authenticationFacade.isLogin()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        boolean isRejected = false;

        Page<UserReport> reportPage = userReportRepository.findAllByUserOrderByIdDesc(user, page);

        List<MyPageReportResponse> myPageReportResponses = new ArrayList<>();

        for(UserReport userReport : reportPage) {
            Report report = reportRepository.findById(userReport.getReport().getId())
                    .orElseThrow(ReportNotFoundException::new);

            if(report.getComment() != null)
                isRejected = true;

            myPageReportResponses.add(
                    MyPageReportResponse.builder()
                            .reportId(report.getId())
                            .title(report.getTitle())
                            .type(report.getType())
                            .createdAt(report.getCreatedAt())
                            .isSubmitted(report.getIsSubmitted())
                            .isAccepted(report.getIsAccepted())
                            .isRejected(isRejected)
                            .build()
            );
            isRejected = false;
        }

        return ProfileReportListResponse.builder()
                .totalElements(reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .myProfileReportResponses(myPageReportResponses)
                .build();
    }

}
