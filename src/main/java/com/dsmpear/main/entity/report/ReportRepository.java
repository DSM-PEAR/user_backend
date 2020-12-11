package com.dsmpear.main.entity.report;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    // 보고서 갖고오기
    Optional<Report> findByReportId(Integer reportId);
    // 보고서 가져오기
    Optional<Report> findAllByFieldAndGradeAndIsAcceptedAndAccess(Field field, Grade grade, Integer isAccepted,Access access);
}