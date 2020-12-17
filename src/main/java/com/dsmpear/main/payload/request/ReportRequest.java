package com.dsmpear.main.payload.request;

import com.dsmpear.main.entity.report.Access;
import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
import com.dsmpear.main.entity.report.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private String languages;

    @NotNull
    private Type type;

    @NotNull
    private Access access;

    @NotNull
    private Field field;

    @NotNull
    private Grade grade;

    @NotNull
    private boolean isSubmitted;

    @NotNull
    private String fileName;

    @NotNull
    private String github;

    private String teamName;

}