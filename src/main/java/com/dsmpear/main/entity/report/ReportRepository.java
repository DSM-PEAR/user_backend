package com.dsmpear.main.entity.report;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    // 보고서 갖고오기
    Optional<Report> findById(Integer reportId);

    // 도전!
    @Query("SELECT a FROM Report a WHERE a.isAccepted = true AND a.isSubmitted = true AND a.access = :access AND a.grade = :grade AND (:field IS NULL OR a.field = :field) AND (:type IS NULL OR a.type = :type)")
    Page<Report> findAllByAccessAndGradeAndFieldAndType(@Param("access") Access access, @Param("grade") Grade grade, @Param("field") Field field, @Param("type") Type type, Pageable page);

    //제목 검색 ORM
    Page<Report> findAllByAccessAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContainsOrderByCreatedAtDesc(Access access, String title, Pageable page);
}