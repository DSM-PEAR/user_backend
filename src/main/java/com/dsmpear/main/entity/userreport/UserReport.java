package com.dsmpear.main.entity.userreport;

import com.dsmpear.main.entity.report.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "user_report_tbl")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private Report members;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;
}
