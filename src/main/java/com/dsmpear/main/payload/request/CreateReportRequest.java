package com.dsmpear.main.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dsmpear.main.entity.report.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReportRequest {
    @NotNull
    private Grade grade;

    @NotNull
    private Access access;

    @NotNull
    private Type type;

    @NotBlank
    private String title;

    @NotNull
    private String languages;

    @NotBlank
    private String description;

    @NotNull
    private String fileName;

    private LocalDateTime createdAt;
}