package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.MyPageResponse;
import com.dsmpear.main.service.mypage.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/{userEmail}")
    public MyPageResponse getMyPage(@PathVariable String userEmail){
        return myPageService.getMypage(userEmail);
    }

    @PutMapping("/{userEmail}")
    public void setSelfIntro(
            @PathVariable String userEmail,
            @RequestParam String intro){
        myPageService.setSelfIntro(userEmail, intro);
    }
}
