package com.dsmpear.main.service.report;

import com.dsmpear.main.payload.request.CreateReportRequest;
import com.dsmpear.main.payload.response.ReportContentResponse;

import javax.servlet.http.HttpServletRequest;

public interface ReportService {
    void writeReport(CreateReportRequest createReportRequest);

    ReportContentResponse viewReport(Integer reportId);

    public Integer updateReport(Integer boardId, String title, String description);

    public void deleteReport(Integer reportId);

}