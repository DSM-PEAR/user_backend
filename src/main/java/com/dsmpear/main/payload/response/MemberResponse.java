package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

    private String memberEmail;

    private String memberName;

}
