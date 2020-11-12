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
    private Grade grade;

    @NotNull
    private Access access;

    @NotNull
    private Type type;

    @NotBlank
    private int reportId;

    @NotBlank
    private String title;

    @NotNull
    private String languages;

    @NotBlank
    private String description;
}

enum Grade {
    grade1,
    grade2,
    grade3,
    graduated;
}

enum Access {
    admin,
    user,
    every;
}

enum Type {
    team,
    sole,
    circles;
}