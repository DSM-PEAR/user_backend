package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReportCommentsResponse {

    private Integer commentId;

    private String content;

    private LocalDateTime createdAt;

    private String userEmail;

    private Boolean isMine;

}
