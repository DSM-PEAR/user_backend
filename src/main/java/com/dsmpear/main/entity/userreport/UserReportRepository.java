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

    Page<UserReport> findAllByUserAndReportIsAcceptedTrueAndReportIsSubmittedTrueAndReportAccessOrderByReportCreatedAtDesc(User user, Access access, Pageable pageable);

    @Transactional
    void deleteAllByReportId(Integer ReportId);

    List<UserReport> findAllBy();
}
