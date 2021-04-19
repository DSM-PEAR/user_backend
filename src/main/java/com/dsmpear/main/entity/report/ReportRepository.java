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

    //제목 검색 ORM
    Page<Report> findAllByAccessAndIsAcceptedTrueAndIsSubmittedTrueAndTitleContainsOrderByCreatedAtDesc(Access access, String title, Pageable page);
}