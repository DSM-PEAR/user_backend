package com.dsmpear.main.service.report;

import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
import com.dsmpear.main.entity.report.Type;
import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.payload.response.ReportListResponse;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    Integer writeReport(ReportRequest reportRequest);
    ReportContentResponse viewReport(Integer reportId);
    Integer updateReport(Integer reportId, ReportRequest reportRequest);
    void deleteReport(Integer reportId);
    ReportListResponse getReportList(Pageable page, Type type, Field field, Grade grade);

}