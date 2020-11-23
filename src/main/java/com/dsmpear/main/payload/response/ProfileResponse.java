package com.dsmpear.main.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    private String name;

    private String email;

    private String selfIntro;

}
