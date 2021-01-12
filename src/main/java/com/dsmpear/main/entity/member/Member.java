package com.dsmpear.main.entity.member;

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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_tbl")
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name="report_id")
    private Integer reportId;

    @Column(length = 30,nullable = false, name= "user_email")
    private String userEmail;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "report")
    private Report report;

}
