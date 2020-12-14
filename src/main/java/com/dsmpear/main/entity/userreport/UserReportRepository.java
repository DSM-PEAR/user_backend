package com.dsmpear.main.entity.userreport;


import com.dsmpear.main.entity.report.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserReportRepository extends CrudRepository<UserReport,Integer> {
    Page<Report> findAllByUserEmail(String userEmail, Pageable page);

    Optional<UserReport> findByReportIdAndUserEmail(Integer reportId, String userEmail);
}
