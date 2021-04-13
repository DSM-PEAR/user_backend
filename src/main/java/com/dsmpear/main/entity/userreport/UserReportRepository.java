package com.dsmpear.main.entity.userreport;


import com.dsmpear.main.entity.report.Access;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserReportRepository extends CrudRepository<UserReport,Integer> {
    Page<UserReport> findAllByUserOrderByIdDesc(User user, Pageable page);
    Optional<UserReport> findByReportAndUser(Report report, User user);

//    @Query("SELECT a FROM UserReport a INNER JOIN Report b ON b.id = a.report.id AND b.isAccepted = true AND b.isSubmitted = true AND b.access = ?1 INNER JOIN User c ON c.email = a.user.email AND a.user.email = ?2 ORDER BY b.createdAt")
//    Page<UserReport> findAllByUserEmail(@Param("access") Access access, @Param("userEmail") String email, Pageable page);

    Page<UserReport> findAllByUserAndReportIsAcceptedTrueAndReportIsSubmittedTrueAndReportAccessOrderByReportCreatedAtDesc(User user, Access access, Pageable pageable);

    @Transactional
    void deleteAllByReportId(Integer ReportId);

    List<UserReport> findAllBy();
}
