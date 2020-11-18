package com.dsmpear.main.service.comment;

import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.payload.request.CommentResponse;
import com.dsmpear.main.payload.response.ReportCommentsResponse;

public class CommentServiceImpl implements CommentService{
    private final ReportRepository reportRepository;

    public CommentServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportCommentsResponse viewComments(Integer reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);
    }
}
