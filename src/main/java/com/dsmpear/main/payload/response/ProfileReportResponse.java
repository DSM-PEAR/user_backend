package com.dsmpear.main.payload.response;

import com.dsmpear.main.entity.report.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileReportResponse {

    private Integer reportId;

    private String title;

    private Type type;

    private LocalDateTime createdAt;

}
