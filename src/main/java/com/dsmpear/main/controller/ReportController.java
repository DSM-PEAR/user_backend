package com.dsmpear.main.controller;

import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;


    @PostMapping
    public void writeReport(@RequestBody @Valid ReportRequest reportRequest) {
        reportService.writeReport(reportRequest);
    }

    @GetMapping("/{reportId}")
    public ReportContentResponse getReportContent(@PathVariable Integer reportId) {
        return reportService.viewReport(reportId);
    }

    @PatchMapping("/{reportId}")
    public Integer updateReport(@PathVariable Integer reportId, @RequestBody ReportRequest reportRequest) {
        return reportService.updateReport(reportId, reportRequest);
    }

    @DeleteMapping("{reportId}")
    public void deleteReport(@PathVariable Integer reportId) {
        reportService.deleteReport(reportId);
    }

}