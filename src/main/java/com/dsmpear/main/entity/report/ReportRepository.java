package com.dsmpear.main.entity.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    // 보고서 갖고오기
    Optional<Report> findById(Integer reportId);

    // 필터가 분야, 타입 모두 적용시 ORM
    Page<Report> findAllByAccessAndFieldAndTypeAndGradeAndIsAcceptedTrueAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Field field, Type type, Grade grade, Pageable page);

    // 필터가 학년만 적용시 ORM
    Page<Report> findAllByAccessAndGradeAndIsAcceptedTrueAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Grade grade, Pageable page);

    // 필터가 타입만 적용시 ORM
    Page<Report> findAllByAccessAndTypeAndGradeAndIsAcceptedTrueAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Type type, Grade grade, Pageable page);

    // 필터가 분야만 적용시 ORM
    Page<Report> findAllByAccessAndFieldAndGradeAndIsAcceptedTrueAndIsSubmittedTrueOrderByCreatedAtDesc(Access access, Field field,Grade grade, Pageable page);

    //제목 검색 ORM
    Page<Report> findAllByAccessAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContainsOrderByCreatedAtDesc(Access access, String title, Pageable page);
}