package com.dsmpear.main.entity.report;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.userreport.UserReport;
import com.dsmpear.main.payload.request.ReportRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private int isAccepted;

    @Column(nullable = false)
    private String languages;

    @Column(name = "file_name",nullable = false)
    private String fileName;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "report", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Member> members;

    @OneToOne(mappedBy = "report")
    @JsonBackReference
    private UserReport userReport;

    public Report update(ReportRequest reportRequest) {
        this.title = reportRequest.getTitle();
        this.description = reportRequest.getDescription();
        this.type = reportRequest.getType();
        this.access = reportRequest.getAccess();
        this.field = reportRequest.getField();
        this.grade = reportRequest.getGrade();
        this.fileName = reportRequest.getFileName();
        this.languages = reportRequest.getLanguages();
        return this;
    }

}
