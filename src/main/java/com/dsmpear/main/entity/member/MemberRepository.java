package com.dsmpear.main.entity.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member,Integer> {

    //팀 아이디와 유저이메일로 찾기
    Member findByTeamIdAndUserEmail(Integer teamId, String userEmail);
    //팀 아이디로 멤버찾기
    List<Member> findAllByTeamId(Integer teamId);
}
