package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberListResponse {

    private List<MemberResponse> memberResponses;

}
