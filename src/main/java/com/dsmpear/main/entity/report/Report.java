package com.dsmpear.main.entity.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public Report update(String title, String description) {
        this.title = title;
        this.description = description;
        return this;
    }
}
