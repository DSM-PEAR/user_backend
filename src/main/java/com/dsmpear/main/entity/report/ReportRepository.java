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

    // 필터가 분야, 타입 모두 적용시 ORM
    Page<Report> findAllByAccessAndFieldAndTypeAndGradeAndIsAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Field field, Type type, Grade grade, Integer isAccepted, Pageable page);

    // 필터가 학년만 적용시 ORM
    Page<Report> findAllByAccessAndGradeAndIsAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Grade grade, Integer isAccepted, Pageable page);

    // 필터가 타입만 적용시 ORM
    Page<Report> findAllByAccessAndTypeAndGradeAndIsAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Type type,Grade grade, Integer isAccepted, Pageable page);

    // 필터가 분야만 적용시 ORM
    Page<Report> findAllByAccessAndFieldAndGradeAndIsAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Field field,Grade grade, Integer isAccepted, Pageable page);

    //제목 검색 ORM
    Page<Report> findAllByAccessAndIsAcceptedAndIsSubmittedTrueAndTitleContainingOrderByCreatedAtDesc(Access access, String title, Integer isAccepted, Pageable page);
}