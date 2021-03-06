package com.dsmpear.main.entity.userreport;


import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.user.User;
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

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "report")
    private Report report;

}
