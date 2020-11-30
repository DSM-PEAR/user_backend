package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyPageReportResponse {

    private Integer reportId;

    private String title;

    private String teamName;

    private Integer isAccepted;

    private LocalDateTime createdAt;

}
