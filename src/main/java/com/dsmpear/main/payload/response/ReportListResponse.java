package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReportListResponse {

    private Integer totalReports;

    private Integer totalPages;

    private List applicationResponses;

}
