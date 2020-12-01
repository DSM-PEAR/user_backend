package com.dsmpear.main.payload.response;

import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApplicationReportListResponse {

    private int totalElements;

    private int totalPages;

    private List applicationResponses;

    private LocalDateTime createdAt;

    private Field field;

    private Grade grade;

}

