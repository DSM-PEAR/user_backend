package com.dsmpear.main.entity.report;

import org.springframework.data.domain.Page;
<<<<<<< HEAD
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
=======
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

>>>>>>> report
import java.util.List;
import java.util.Optional;


@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
    Optional<Report> findByReportId(Integer reportId);
    List<Report> findByAccess(Access access);
<<<<<<< HEAD
    Page<Report> findAllBy(Pageable page);
=======
    Page<Report> findAllBy(Pageable pageable);
>>>>>>> report
}
