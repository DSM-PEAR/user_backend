package com.dsmpear.main.entity.report;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    // 보고서 갖고오기
    Optional<Report> findByReportId(Integer reportId);

    // 필터가 분야, 타입 모두 적용시 ORM
    Page<Report> findAllByAccessAndFieldAndTypeAndGradeAndIsAcceptedTrueAndIsSubmittedTrue(Access access, Field field, Type type, Grade grade, Pageable page);

    // 필터가 학년만 적용시 ORM
    Page<Report> findAllByAccessAndGradeAndIsAcceptedTrueAndIsSubmittedTrue(Access access, Grade grade, Pageable page);

    // 필터가 타입만 적용시 ORM
    Page<Report> findAllByAccessAndTypeAndGradeAndIsAcceptedTrueAndIsSubmittedTrue(Access access, Type type,Grade grade, Pageable page);

    // 필터가 분야만 적용시 ORM
    Page<Report> findAllByAccessAndFieldAndGradeAndIsAcceptedTrueAndIsSubmittedTrue(Access access, Field field,Grade grade, Pageable page);

    //제목 검색 ORM
    Page<Report> findAllByAccessAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContaining(Access access, String title, Pageable page);
}