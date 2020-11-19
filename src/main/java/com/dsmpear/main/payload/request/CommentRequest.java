package com.dsmpear.main.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotNull
    private Long reportId;

    @NotNull
    private String userEmail;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private String content;


}
