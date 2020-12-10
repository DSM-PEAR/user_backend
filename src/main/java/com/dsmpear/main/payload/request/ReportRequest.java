package com.dsmpear.main.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dsmpear.main.entity.report.*;

import java.time.LocalDateTime;

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

}