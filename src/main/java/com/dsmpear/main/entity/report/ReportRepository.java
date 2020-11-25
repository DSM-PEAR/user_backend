package com.dsmpear.main.entity.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    Optional<Report> findByReportId(Integer reportId);
    List<Report> findByAccess(Access access);
    Page<Report> findAllBy(Pageable page);
}
