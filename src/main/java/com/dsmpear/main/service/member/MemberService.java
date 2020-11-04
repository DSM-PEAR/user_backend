package com.dsmpear.main.service.member;

import com.dsmpear.main.payload.request.MemberRequest;

public interface MemberService {
    void setMember(MemberRequest memberRequest);
    void deleteMember(String userEmail);
}
