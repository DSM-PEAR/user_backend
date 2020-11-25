package com.dsmpear.main.entity.report;

import org.springframework.data.domain.Page;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

=======
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
>>>>>>> e1dcb72153cbca7487596f49868e33c5a2ea3066
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    // 보고서 갖고오기
    Optional<Report> findByReportId(Integer reportId);
<<<<<<< HEAD
    Page<Report> findAllByField(Pageable page, Field field);
    // 제목으로 검색
    Page<Report> findAllByTitleContainsAndAccessOrderByCreatedAt(Pageable page, String query, Access access);
    // 언어로 검색
    Page<Report> findAllByLanguagesContainsAndAccessOrderByCreatedAt(Pageable page, String query,Access access);
=======
    List<Report> findByAccess(Access access);
    Page<Report> findAllBy(Pageable pageable);
>>>>>>> e1dcb72153cbca7487596f49868e33c5a2ea3066
}

