package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.CreateReportRequest;
import com.dsmpear.main.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public void writeReport(@RequestBody @Valid CreateReportRequest createReportRequest) {
        reportService.writeReport(createReportRequest);
    }
}