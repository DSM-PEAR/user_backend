package com.dsmpear.main.service.mypage.mypage;

import com.dsmpear.main.payload.response.ProfilePageResponse;

public interface MyPageService {
    ProfilePageResponse getMyPage();
    void setSelfIntro(String intro, String gitHub);
}
