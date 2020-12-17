package com.dsmpear.main.entity.userreport;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserReportRepository extends CrudRepository<UserReport,Integer> {
    Page<UserReport> findAllByUserEmail(String userEmail, Pageable page);

    Optional<UserReport> findByReportIdAndUserEmail(Integer reportId, String userEmail);

    @Transactional
    Optional<UserReport> deleteAllByReportId(Integer ReportId);
}
