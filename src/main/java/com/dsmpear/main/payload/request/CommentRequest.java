package com.dsmpear.main.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotNull
    private Integer reportId;

    @NotNull
    private String userEmail;

    @NotNull
    private String content;

}
