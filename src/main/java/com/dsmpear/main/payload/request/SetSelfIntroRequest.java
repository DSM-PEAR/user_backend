package com.dsmpear.main.payload.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SetSelfIntroRequest {

    private String intro;

    private String github;

}
