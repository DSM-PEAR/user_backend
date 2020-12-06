package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberListResponse extends PageResponse{

    private List<MemberResponse> memberResponses;

    @Builder
    public MemberListResponse(int totalElements, int totalPages, List<MemberResponse> memberResponses) {
        super(totalElements, totalPages);
        this.memberResponses = memberResponses;
    }
}
