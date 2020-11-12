package com.dsmpear.main.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class CreateReportRequest {
    @NotNull
    private enum type {
        admin,
        user,
        every
    }

    @NotBlank
    private int reportId;

    @NotBlank
    private String title;

    private String comment;

    @NotNull
    private String languages;

    @NotBlank
    private String description;
    
    @NotNull
    private enum grade;

    @NotNull
    private enum access;
}
