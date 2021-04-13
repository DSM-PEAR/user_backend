package com.dsmpear.main.entity.reportfile;

import com.dsmpear.main.entity.report.Report;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_file_tbl")
public class ReportFile {

    @Id
    @GeneratedValue
    private Long id;

    private String path;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "report_id")
    private Report report;
}
