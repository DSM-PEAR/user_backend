package com.dsmpear.main.entity.userreport;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_report_tbl")
@Entity
public class UserReport {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private String userEmail;

    @Column(name = "report_id", nullable = false)
    private Integer reportId;

}
