package com.dsmpear.main.service.profile.profileReport;

import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
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
public class ProfileReportListServiceImpl implements ProfileReportListService{

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;


    @Override
    public ProfileReportListResponse getReport(String email, Pageable page) {

        Page<Report> profileReportPage = reportRepository.findAllBy(page);

        List<ProfileReportResponse> profileReportListResponses = new ArrayList<>();

        for(Report report : profileReportPage){
            if(memberRepository.findByTeamIdAndUserEmail(report.getReportId(),email)!=null){
                profileReportListResponses.add(
                        ProfileReportResponse.builder()
                                .reportId(report.getReportId())
                                .title(report.getTitle())
                                .isAccepted(report.getIsAccepted())
                                .createdAt(report.getCreatedAt())
                                .build()
                );
            }
        }

        return ProfileReportListResponse.builder()
                .totalElements((int) profileReportPage.getTotalElements())
                .totalPages(profileReportPage.getTotalPages())
                .profileReportResponses(profileReportListResponses)
                .build();
    }
}
