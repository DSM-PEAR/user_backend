package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

    private Integer memberId;

    private String membeEmail;

    private String memberName;
}
