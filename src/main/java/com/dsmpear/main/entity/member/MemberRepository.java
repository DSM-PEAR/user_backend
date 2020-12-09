package com.dsmpear.main.entity.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member,Integer> {
    Optional<Member> findByReportIdAndUserEmail(Integer reportId, String userEmail);
}
