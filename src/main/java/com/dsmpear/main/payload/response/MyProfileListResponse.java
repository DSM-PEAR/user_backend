package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyProfileListResponse {

    private Integer reportId;

    private String title;

    private Integer isAccepted;

    private LocalDateTime createdAt;

}
