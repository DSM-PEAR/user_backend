package com.dsmpear.main.service.report;

import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.payload.request.CreateReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;

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
}
