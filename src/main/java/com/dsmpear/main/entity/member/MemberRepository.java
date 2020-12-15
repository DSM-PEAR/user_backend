package com.dsmpear.main.entity.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member,Integer> {
    Page<Member> findAllByReportId(Integer reportId, Pageable page);
    Optional<Member> findByReportIdAndUserEmail(Integer reportId, String userEmail);
}
