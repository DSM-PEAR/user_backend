package com.dsmpear.main.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String name;

    private String email;

}
