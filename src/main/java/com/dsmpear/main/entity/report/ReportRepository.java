package com.dsmpear.main.entity.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    // 보고서 갖고오기
    Optional<Report> findByReportId(Integer reportId);
//    Optional<Report> findAllByFieldAndGradeAndIsAcceptedAndAccess_UserOrAccess_EveryOrderByCreatedAt(Field field, Grade grade, Integer isAccepted,Access access);
}