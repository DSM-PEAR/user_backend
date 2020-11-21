package com.dsmpear.main.entity.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "report_tbl")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Grade grade;

    @Column(nullable = false)
    private Access access;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private int isAccepted;

    @Column(nullable = false)
    private String languages;

}
