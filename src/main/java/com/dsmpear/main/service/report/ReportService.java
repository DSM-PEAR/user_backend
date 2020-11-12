package com.dsmpear.main.service.report;

import com.dsmpear.main.payload.request.CreateReportRequest;

public interface ReportService {
    void writeReport(CreateReportRequest createReportRequest);
}
