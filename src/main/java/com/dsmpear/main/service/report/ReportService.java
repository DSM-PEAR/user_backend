package com.dsmpear.main.service.report;

import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ReportContentResponse;

public interface ReportService {
    void writeReport(ReportRequest reportRequest);

    ReportContentResponse viewReport(Integer reportId);

    public Integer updateReport(Integer boardId, ReportRequest reportRequest);

    public void deleteReport(Integer reportId);

}