package com.dsmpear.main.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dsmpear.main.entity.report.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateReportRequest {
    @NotNull
    private Grade grade;

    @NotNull
    private Access access;

    @NotNull
    private Type type;

    @NotNull
    private Field field;

    @NotBlank
    private String title;

    @NotNull
    private String languages;

    @NotBlank
    private String description;

    @NotNull
    private String fileName;

    @NotNull
    private Integer isAccpeted;

    private LocalDateTime createdAt;
}