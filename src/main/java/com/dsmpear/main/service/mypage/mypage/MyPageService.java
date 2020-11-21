package com.dsmpear.main.service.mypage.mypage;

import com.dsmpear.main.payload.response.MyPageResponse;

public interface MyPageService {
    MyPageResponse getMypage(String userEmail);
    void setSelfIntro(String userEmail, String intro);
}
