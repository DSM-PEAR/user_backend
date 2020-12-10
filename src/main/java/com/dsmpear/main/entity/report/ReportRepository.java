package com.dsmpear.main.entity.report;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    // 보고서 갖고오기
    Optional<Report> findByReportId(Integer reportId);
    List<Report> findAllByAccessAndFieldAndTypeAndGradeAndIsAcceptedTrueAndIsSubmittedTrue(Access access, Field field, Type type,Grade grade);
    List<Report> findAllByAccessAndFieldAndTypeAndGradeAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContaining(Access access, Field field, Type type,Grade grade, String title);
}