package com.dsmpear.main.payload.response;

import com.dsmpear.main.entity.report.Access;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProfileReportListResponse {

    private String title;

    private Access access;

    private LocalDateTime createdAt;

}
