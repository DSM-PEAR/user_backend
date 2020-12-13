package com.dsmpear.main.entity.report;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.payload.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "report_tbl")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Grade grade;

    @Column(nullable = false)
    private Access access;

    @Column(nullable = false)
    private Field field;

    @Column(nullable = false)
    private Type type;

    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted;

    @Column(name = "is_submitted", nullable = false)
    private boolean isSubmitted;

    @Column(name = "file_name",nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String github;

    @Column(nullable = false)
    private String languages;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "report")
    private List<Member> members;

    public Report update(ReportRequest reportRequest) {
        this.title = reportRequest.getTitle();
        this.description = reportRequest.getDescription();
        this.languages = reportRequest.getLanguages();
        this.type = reportRequest.getType();
        this.access = reportRequest.getAccess();
        this.field = reportRequest.getField();
        this.grade = reportRequest.getGrade();
        this.isSubmitted = reportRequest.isSubmitted();
        this.fileName = reportRequest.getFileName();
        this.github = reportRequest.getGithub();
        return this;
    }

}
