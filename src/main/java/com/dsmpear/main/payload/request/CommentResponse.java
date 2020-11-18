package com.dsmpear.main.payload.request;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CommentResponse {
    @NotNull
    private Long reportId;

    @NotNull
    private Long userId;

    @NotNull
    private String userEmail;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private String content;
}
