package com.dsmpear.main.controller;

import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
<<<<<<< HEAD
import com.dsmpear.main.entity.report.Type;
=======
>>>>>>> 425d191eda7450eb2b991534eda7cd68a7a28a7b
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
<<<<<<< HEAD
    public ReportListResponse getReportList(@RequestParam(required = false) Field field,
                                            @RequestParam(required = false) Type type,
                                            @RequestParam Grade grade,
                                            Pageable page) {
        return reportService.getReportList(page,type,field,grade);
=======
    public ReportListResponse getReportList(@RequestParam Field field,
                                            @RequestParam Grade grade,
                                            @PathVariable Pageable page) {
        return reportService.getReportList(page,field,grade);
>>>>>>> 425d191eda7450eb2b991534eda7cd68a7a28a7b
    }

}
