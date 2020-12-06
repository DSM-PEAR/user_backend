package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.ProfilePageResponse;
import com.dsmpear.main.service.mypage.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class MyPageController {

    private final MyPageService myPageService;
    /*private final MyPageReportListService myPageReportListService;*/

    @GetMapping
    public ProfilePageResponse getMyPage(){
        return myPageService.getMyPage();
    }

    @PutMapping
    public void setSelfIntro(@RequestParam String intro, @RequestParam String gitHub){
        myPageService.setSelfIntro(intro, gitHub);
    }

    /*@GetMapping("/report")
    public ProfileReportListResponse getReport(Pageable page){
        return myPageReportListService.getReport(page);
    }*/

}
