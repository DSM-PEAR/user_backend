package com.dsmpear.main.service.member;

import com.dsmpear.main.payload.request.MemberRequest;
import com.dsmpear.main.payload.response.MemberListResponse;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberListResponse getMember(Integer reportId, Pageable page);
    void addMember(MemberRequest memberRequest);
    void deleteMember(Integer memberId);
}
