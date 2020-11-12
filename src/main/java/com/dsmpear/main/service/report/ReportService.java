package com.dsmpear.main.service.report;

import com.dsmpear.main.payload.request.CreateReportRequest;

public interface ReportService {
    Integer writeReport(CreateReportRequest createReportRequest);
}
