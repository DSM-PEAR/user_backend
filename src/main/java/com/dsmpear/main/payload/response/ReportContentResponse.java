package com.dsmpear.main.payload.response;

import com.dsmpear.main.entity.report.Access;
import com.dsmpear.main.entity.report.Field;
import com.dsmpear.main.entity.report.Grade;
import com.dsmpear.main.entity.report.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportContentResponse {

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private String languages;

    private String fileName;

    private Type type;

    private Grade grade;

    private Access access;

    private Field field;

    private Boolean isMine;

    private List<ReportCommentsResponse> comments;

    private String teamName;

    private String comment;

    private List<MemberResponse> member;

    private Long fileId;

}
