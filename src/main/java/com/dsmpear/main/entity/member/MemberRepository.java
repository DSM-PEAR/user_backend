package com.dsmpear.main.entity.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member,Integer> {

    //팀 아이디와 유저이메일로 찾기
    Optional<Member> findByTeamIdAndUserEmail(Integer teamId, String userEmail);
    //팀 아이디로 멤버찾기
    List<Member> findAllByTeamId(Integer teamId);
    //팀 아이디로 팀 삭제하기
    void deleteAllByTeamId(Integer teamId);
    //보고서 아이디로 팀 삭제하기
    void deleteAllByReportId(Integer reportId);

}
