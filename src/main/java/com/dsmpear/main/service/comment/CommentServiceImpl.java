package com.dsmpear.main.service.comment;

import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.payload.request.CommentResponse;
import com.dsmpear.main.payload.response.ReportCommentsResponse;

public class CommentServiceImpl implements CommentService{
    private ReportRepository reportRepository;

}
