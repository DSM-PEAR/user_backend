package com.dsmpear.main.payload.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRequest {

    private Integer teamId;

    private String userEmail;

}
