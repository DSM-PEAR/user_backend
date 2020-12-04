package com.dsmpear.main.payload.response;

import com.dsmpear.main.entity.report.Access;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProfileReportResponse {

    private Integer reportId;

    private String title;

    private String teamName;

    private Integer isAccepted;

    private Access access;

    private LocalDateTime createdAt;

}