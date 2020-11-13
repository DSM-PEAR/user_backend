package com.dsmpear.main.entity.team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<Team,Integer> {
    Optional<Team> findAllByReportId(Integer reportId);
    Optional<Team> findByReportId(Integer reportId);
    void deleteAllByReportId(Integer reportId);
}
