package com.dsmpear.main.controller;

import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
import com.dsmpear.main.entity.report.Type;
import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report/filter")
public class ReportListController {
    private final ReportService reportService;
    @GetMapping("/{size}/{page}")
    public ReportListResponse getReportList(@RequestParam(required = false) Field field,
                                            @RequestParam(required = false) Type type,
                                            @RequestParam Grade grade,
                                            Pageable page) {
        return reportService.getReportList(page,type,field,grade);
    }

}
