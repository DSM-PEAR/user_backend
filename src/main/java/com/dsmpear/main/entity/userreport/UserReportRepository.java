package com.dsmpear.main.entity.userreport;


import com.dsmpear.main.entity.report.Access;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserReportRepository extends CrudRepository<UserReport,Integer> {
    Page<UserReport> findAllByUserEmailOrderByReportIdDesc(String userEmail, Pageable page);
    Optional<UserReport> findByReportIdAndUserEmail(Integer reportId, String userEmail);
    @Query("SELECT a FROM UserReport a INNER JOIN Report b ON b.id = a.reportId AND b.isAccepted = true AND b.isSubmitted = true AND b.access = ?1 INNER JOIN User c ON c.email = a.userEmail AND a.userEmail = ?2 ORDER BY b.createdAt")
    Page<UserReport> findAllByUserEmail(@Param("access") Access access, @Param("userEmail") String email, Pageable page);

    @Transactional
    Optional<UserReport> deleteAllByReportId(Integer ReportId);
}
