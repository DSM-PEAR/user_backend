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
    Page<Report> findAllByAccessAndFieldAndTypeAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Field field, Type type, Grade grade, Integer isAccepted, Pageable page);

    // 필터가 학년만 적용시 ORM
    Page<Report> findAllByAccessAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Grade grade, Integer isAccepted, Pageable page);

    // 필터가 타입만 적용시 ORM
    Page<Report> findAllByAccessAndTypeAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Type type,Grade grade, Integer isAccepted, Pageable page);

    // 필터가 분야만 적용시 ORM
    Page<Report> findAllByAccessAndFieldAndGradeAndAcceptedAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Field field,Grade grade, Integer isAccepted, Pageable page);

    //제목 검색 ORM
    Page<Report> findAllByAccessAndAcceptedAndIsSubmittedTrueAndTitleContainingOrderByCreatedAtDesc(Access access,  Integer accepted,String title, Pageable page);
}