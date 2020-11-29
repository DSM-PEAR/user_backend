package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponse {

    private String userName;

    private String userEmail;

    private String selfIntro;

}
