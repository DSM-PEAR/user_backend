package com.dsmpear.main.controller;

<<<<<<< HEAD
import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.payload.request.ReportRequest;
import com.dsmpear.main.payload.response.ReportContentResponse;
import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> 1cc64c60be027171bdb8482833ee3dc4ca962435

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    /*private final ReportService reportService;

<<<<<<< HEAD
    @GetMapping("/{type}")
    public ReportListResponse getReportList(@PathVariable Field field,
                                            Pageable page) {
        return reportService.getReportList(page, field);
    }
=======
   *//* @GetMapping("/{type}")
    public ApplicationListResponse getReportList(@PathVariable String type,
                                                 @RequestParam("query") String query,
                                                 Pageable page) {
        return reportService.searchReport(page,type,query);
    }*//*
>>>>>>> 1cc64c60be027171bdb8482833ee3dc4ca962435

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
    }*/

}