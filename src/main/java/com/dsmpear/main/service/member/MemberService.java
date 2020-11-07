package com.dsmpear.main.service.member;

import com.dsmpear.main.payload.request.MemberRequest;

public interface MemberService {
    void addMember(MemberRequest memberRequest);
    void deleteMember(Integer memberId);
}
