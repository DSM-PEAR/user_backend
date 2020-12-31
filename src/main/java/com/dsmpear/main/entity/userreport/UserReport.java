package com.dsmpear.main.entity.userreport;


import com.dsmpear.main.entity.report.Report;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Table(name = "user_report_tbl")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserReport {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private String userEmail;

    @Column(name = "report_id", nullable = false)
    private Integer reportId;
}
