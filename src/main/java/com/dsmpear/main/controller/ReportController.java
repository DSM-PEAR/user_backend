package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.CreateReportRequest;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public void writeReport(@RequestBody @Valid CreateReportRequest createReportRequest) {
        reportService.writeReport(createReportRequest);
    }

    @GetMapping("/{reportId}")
    public ReportContentResponse getReportContent(@PathVariable Integer reportId) {
        return getReportContent(reportId);
    }

    @PatchMapping("/{reportId}")
    public Integer updateReport(@PathVariable Integer reportId,
                                @RequestParam String title,
                                @RequestParam String description) {
        return reportService.updateReport(reportId,title,description);
    }

    @DeleteMapping("{reportId}")
    public void deleteReport(@PathVariable Integer reportId) {
        reportService.deleteReport(reportId);
    }

}