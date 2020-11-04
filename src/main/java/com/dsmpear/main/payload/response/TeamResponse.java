package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class TeamResponse {

    private Integer teamId;

    private String teamName;

    private String userEmail;

    private List<MemberResponse> memberList;

}
