package com.dsmpear.main.service.report;

import com.dsmpear.main.payload.request.CreateReportRequest;

import java.util.ArrayList;

public interface ReportService {
    void writeReport(CreateReportRequest createReportRequest);
}