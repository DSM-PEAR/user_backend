package com.dsmpear.main.service.report;

import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ApplicationListResponse;
import com.dsmpear.main.payload.response.ReportContentResponse;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    void writeReport(ReportRequest reportRequest);

    ReportContentResponse viewReport(Integer reportId);

    public Integer updateReport(Integer boardId, ReportRequest reportRequest);

    public void deleteReport(Integer reportId);

    ApplicationListResponse getReportList(Pageable page, Field field);

    ApplicationListResponse searchReport(Pageable page, String mode, String query);
}