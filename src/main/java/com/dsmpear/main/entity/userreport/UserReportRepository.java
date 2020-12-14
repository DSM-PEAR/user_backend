package com.dsmpear.main.entity.userreport;


import com.dsmpear.main.entity.report.Report;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserReportRepository extends CrudRepository<UserReport,Integer> {
    List<UserReport> findAllByUserEmail(String email);

    Optional<UserReport> findByReportId(Integer reportId);
}
