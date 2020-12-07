package com.dsmpear.main.service.report;

import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ApplicationListResponse;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.payload.response.ReportListResponse;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    void writeReport(ReportRequest reportRequest);

    ReportContentResponse viewReport(Integer reportId);

    public Integer updateReport(Integer reportId, ReportRequest reportRequest);

    public void deleteReport(Integer reportId);

    ReportListResponse getReportList(Pageable page, Field field, Grade grade);

    ReportListResponse searchReport(Pageable page, String mode, String query);
}