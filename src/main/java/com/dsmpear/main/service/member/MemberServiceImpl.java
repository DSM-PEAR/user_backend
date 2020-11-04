package com.dsmpear.main.service.member;

import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.payload.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public void setMember(MemberRequest memberRequest) {

    }

    @Override
    public void deleteMember(Integer userEmail) {

    }
}
